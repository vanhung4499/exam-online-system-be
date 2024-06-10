package com.hnv99.exam.common.group;

public interface UserGroup {

    /**
     * Parameter validation group for user creation
     */
    interface CreateUserGroup extends UserGroup{}

    /**
     * Parameter validation group for user password update
     */
    interface UpdatePasswordGroup extends UserGroup{}

    interface RegisterGroup extends UserGroup{}
}
