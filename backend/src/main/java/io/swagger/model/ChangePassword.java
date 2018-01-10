package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ChangePassword
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-29T20:26:40.636Z")

public class ChangePassword   {
    @JsonProperty("oldPassword")
    private String oldPassword = null;

    @JsonProperty("newPassword")
    private String newPassword = null;

    public ChangePassword oldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    /**
     * not required for admin
     * @return oldPassword
     **/
    @ApiModelProperty(value = "not required for admin")


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public ChangePassword newPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    /**
     * Get newPassword
     * @return newPassword
     **/
    @ApiModelProperty(value = "")


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChangePassword changePassword = (ChangePassword) o;
        return Objects.equals(this.oldPassword, changePassword.oldPassword) &&
                Objects.equals(this.newPassword, changePassword.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ChangePassword {\n");

        sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
        sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
