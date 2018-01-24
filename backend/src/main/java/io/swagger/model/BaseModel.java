package io.swagger.model;

import io.swagger.ModelHelper;
import org.springframework.data.repository.CrudRepository;

import java.lang.*;
import java.lang.Error;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gros on 20.11.17.
 */
public abstract class BaseModel {
    /* Hack to validate dependencies and objects updating.
    *  First, spring can't easily handle partial object update. It sets not sent fields to null,
    *  but we assume that they should remain unchanged.
    *
    *  Second, nested (related) objects validation (like: references to not existing objects,
    *  deletion when some object have reference to current object etc) is done at sql level.
    *  Methods below allows to make validations at controller level
    *  */
    /**
     * Combine (merge) model (send via http) with old model from database
     * @param repository model repository
     * @param model model to merge
     * @param <T> Class of model (Entity, extends BaseModel)
     * @param <R> Class of model repository (extends CrudRepository)
     * @return T, updated Model
     */
    public static <T extends BaseModel, R extends CrudRepository> T combineWithOld(R repository, T model, Integer id) {
        T modelOld = BaseModel.getModelHelper(repository, id);
        try {
            ModelHelper.combine(modelOld, model);
        } catch (Exception e) {
            throw new java.lang.Error("Wrong " + model.getClass().getSimpleName() + " object");
        }
        return model;
    }

    /**
     * Get (find) model (entity) by id
     * @param repository model repository
     * @param id Integer
     * @param <T> Class of model (Entity, extends BaseModel)
     * @param <R> Class of model repository (extends CrudRepository)
     * @return T, model
     */
    public static <T, R extends CrudRepository> T getModelHelper(R repository, Integer id) {
        T model = (T) repository.findOne(id);
        if(model == null)
            throw new java.lang.Error("Model not found");
        return model;
    }

    /**
     * Check dependencies (mainly before deletion from database). Throw error if there are any objects referring to
     * entity that we want delete
     * @param dependentRepository repository of objects that may referring to entity we want delete
     * @param model entity to delete
     * @param <T> Class of model (Entity, extends BaseModel)
     * @param <R> Class of dependent objects repository (extends CrudRepository)
     */
    public static <T extends BaseModel, R extends CrudRepository> void dependent(R dependentRepository, T model) {
        String methodFindAllByModelIdString = "findAllBy" + model.getClass().getSimpleName() + "Id";
        java.lang.reflect.Method methodFindAllByModelId;

        try {
            methodFindAllByModelId = dependentRepository.getClass().getMethod(methodFindAllByModelIdString, Integer.class);
        } catch (SecurityException e) { throw new java.lang.Error("Security error in dependent: " + e); }
          catch (NoSuchMethodException e) { throw new java.lang.Error("Method not found in dependent: " + e); }

        Integer depentendObjectAssignedCount = 0;
        try {
            depentendObjectAssignedCount = ((List) methodFindAllByModelId.invoke(dependentRepository, model.getId())).size();
        } catch (Exception e) { throw new java.lang.Error("Method call error in dependent: " + e); }
        System.out.println(depentendObjectAssignedCount);
        if(depentendObjectAssignedCount != 0)
            throw new java.lang.Error(depentendObjectAssignedCount + " dependent objects are assigned to this " + model.getClass().getSimpleName());
    }

    /**
     * Check dependencies (mainly before create/update). Throw error if entity we want to save is referring to
     * object that doesn't exists
     * @param dependencyClass Class of object that is required to create/update our entity
     * @param repository repository of required objects (corresponding to dependencyClass)
     * @param model entity to create/update
     * @param <D> Class of required object (Entity, extends BaseModel)
     * @param <T> Class of model (Entity, extends BaseModel)
     * @param <R> Class of required objects repository (extends CrudRepository)
     * @return T, model
     */
    public static <D extends BaseModel, T, R extends CrudRepository> T dependsOn(Class dependencyClass, R repository, T model, String dependencySimpleName) {
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

        if(dependencyFromModel.getId() == null)
            throw new java.lang.Error(dependencySimpleName + " id is null");

        D dependencyFound = (D) repository.findOne(dependencyFromModel.getId());
        if(dependencyFound == null)
            throw new java.lang.Error(dependencySimpleName + " not found");

        try {
            setDependency.invoke(model, dependencyFound);
        } catch (Exception e) { throw new java.lang.Error("Method call error2 in dependsOn: " + e); }

        return model;
    }

    public static <D extends BaseModel, T, R extends CrudRepository> T dependsOn(Class dependencyClass, R repository, T model) {
        String dependencySimpleName = dependencyClass.getSimpleName();
        return dependsOn(dependencyClass, repository, model, dependencySimpleName);
    }

    public abstract Integer getId();
}
