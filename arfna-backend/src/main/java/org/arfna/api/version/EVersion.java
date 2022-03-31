package org.arfna.api.version;

import org.arfna.database.DatabaseUtil;
import org.arfna.method.blog.GetBlogApiV1;
import org.arfna.method.blog.IGetBlogApi;
import org.arfna.method.blog.mutation.IMutatePostApi;
import org.arfna.method.blog.mutation.MutatePostApiV1;
import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;
import org.arfna.method.password.IPasswordHelper;
import org.arfna.method.password.PasswordHelperV1;
import org.arfna.method.password.login.api.IMutateSubscriberApi;
import org.arfna.method.password.login.api.MutateSubscriberApiV1;
import org.arfna.method.password.middleware.IMiddlewareHelper;
import org.arfna.method.password.middleware.MiddlewareHelperV1;

public enum EVersion {

    V1(new DummyApiV1(), new MutateSubscriberApiV1(), new MutatePostApiV1(), new GetBlogApiV1(),
            new MiddlewareHelperV1(), new DatabaseUtil(), new PasswordHelperV1());

    private IDummyApi dummyAPI;
    private IMutateSubscriberApi mutateSubscriberApi;
    private IMutatePostApi mutatePostApi;
    private IGetBlogApi getBlogApi;
    private IMiddlewareHelper middlewareHelper;
    private DatabaseUtil databaseUtil;
    private IPasswordHelper passwordHelper;

    EVersion(IDummyApi dummyAPI, IMutateSubscriberApi mutateSubscriberApi, IMutatePostApi mutatePostApi, IGetBlogApi getBlogApi,
             IMiddlewareHelper middlewareHelper, DatabaseUtil databaseUtil, IPasswordHelper passwordHelper) {
        this.dummyAPI = dummyAPI;
        this.mutateSubscriberApi = mutateSubscriberApi;
        this.mutatePostApi = mutatePostApi;
        this.getBlogApi = getBlogApi;
        this.middlewareHelper = middlewareHelper;
        this.databaseUtil = databaseUtil;
        this.passwordHelper = passwordHelper;
    }

    public IDummyApi getDummyAPI() {
        return dummyAPI;
    }

    public DatabaseUtil getDatabaseUtil() {
        return databaseUtil;
    }

    public IPasswordHelper getPasswordHelper() {
        return passwordHelper;
    }

    public IMiddlewareHelper getMiddlewareHelper() {
        return middlewareHelper;
    }

    public IMutateSubscriberApi getMutateSubscriberApi() {
        return mutateSubscriberApi;
    }

    public IMutatePostApi getMutatePostApi() {
        return mutatePostApi;
    }

    public IGetBlogApi getGetBlogApi() {
        return getBlogApi;
    }
}
