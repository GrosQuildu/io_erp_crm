package io.swagger.model;

import io.swagger.ModelHelper;
import org.springframework.data.repository.CrudRepository;

import java.lang.*;
import java.util.List;

/**
 * Created by gros on 20.11.17.
 */
public abstract class BaseModel {

    /**
     *
     * @param repository:
     * @param model
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T extends BaseModel, R extends CrudRepository> T combineWithOld(R repository, T model) {
        T modelOld = BaseModel.getModelHelper(repository, model.getId());
        try {
            ModelHelper.combine(modelOld, model);
        } catch (Exception e) {
            throw new java.lang.Error("Wrong " + model.getClass().getSimpleName() + " object");
        }
        return model;
    }

    public static <T, R extends CrudRepository> T getModelHelper(R repository, Integer id) {
        T model = (T) repository.findOne(id);
        if(model == null)
            throw new java.lang.Error("Model not found");
        return model;
    }

    public static <R extends CrudRepository> void dependent(R dependentRepository, Class modelClass, Integer id) {
        String methodFindAllByModelIdString = "findAllBy" + modelClass.getSimpleName() + "Id";
        java.lang.reflect.Method methodFindAllByModelId;

        try {
            methodFindAllByModelId = dependentRepository.getClass().getMethod(methodFindAllByModelIdString, Integer.class);
        } catch (SecurityException e) { throw new java.lang.Error("Security error in dependent: " + e); }
          catch (NoSuchMethodException e) { throw new java.lang.Error("Method not found in dependent: " + e); }

        Integer depentendObjectAssignedCount = 0;
        try {
            depentendObjectAssignedCount = ((List) methodFindAllByModelId.invoke(dependentRepository, id)).size();
        } catch (Exception e) { throw new java.lang.Error("Method call error in dependent: " + e); }
        System.out.println(depentendObjectAssignedCount);
        if(depentendObjectAssignedCount != 0)
            throw new java.lang.Error(depentendObjectAssignedCount + " dependent objects are assigned to this " + modelClass.getSimpleName());
    }

    public static <D extends BaseModel, T, R extends CrudRepository> T dependsOn(Class dependencyClass, T model, R repository) {
        String dependencySimpleName = dependencyClass.getSimpleName();

        String methodGet = "get" + dependencySimpleName;
        String methodSet = "set" + dependencySimpleName;

        java.lang.reflect.Method getDependency;
        java.lang.reflect.Method setDependency;

        try {
            getDependency = model.getClass().getMethod(methodGet);
        } catch (SecurityException e) { throw new java.lang.Error("Security error in dependsOn: " + e); }
          catch (NoSuchMethodException e) { throw new java.lang.Error("Method not found in dependsOn: " + e); }

        try {
            setDependency = model.getClass().getMethod(methodSet, dependencyClass);
        } catch (SecurityException e) { throw new java.lang.Error("Security2 error in dependsOn: " + e); }
          catch (NoSuchMethodException e) { throw new java.lang.Error("Method2 not found in dependsOn: " + e); }

        D dependencyFromModel = null;
        try {
            dependencyFromModel = (D) getDependency.invoke(model);
        } catch (Exception e) { throw new java.lang.Error("Method call error in dependsOn: " + e); }

        if(dependencyFromModel == null)
            throw new java.lang.Error(dependencySimpleName + " is null");

        D dependencyFound = (D) repository.findOne(dependencyFromModel.getId());
        if(dependencyFound == null)
            throw new java.lang.Error(dependencySimpleName + " not found");

        try {
            setDependency.invoke(model, dependencyFound);
        } catch (Exception e) { throw new java.lang.Error("Method call error2 in dependsOn: " + e); }

        return model;
    }

    public abstract Integer getId();
}
