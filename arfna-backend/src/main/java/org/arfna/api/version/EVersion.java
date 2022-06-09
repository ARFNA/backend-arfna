package org.arfna.api.version;

import org.arfna.database.DatabaseUtil;
import org.arfna.database.s3.S3Util;
import org.arfna.method.blog.GetBlogApiV1;
import org.arfna.method.blog.IGetBlogApi;
import org.arfna.method.blog.images.IImageIdApi;
import org.arfna.method.blog.images.ImageIdApiV1;
import org.arfna.method.blog.mutation.IMutatePostApi;
import org.arfna.method.blog.mutation.MutatePostApiV1;
import org.arfna.method.cookie.subscriber.ISubscriberCookieApi;
import org.arfna.method.cookie.subscriber.SubscriberCookieApiV1;
import org.arfna.method.dummy.DummyApiV1;
import org.arfna.method.dummy.IDummyApi;
import org.arfna.method.password.IPasswordHelper;
import org.arfna.method.password.PasswordHelperV1;
import org.arfna.method.password.login.api.IMutateSubscriberApi;
import org.arfna.method.password.login.api.MutateSubscriberApiV1;
import org.arfna.method.password.middleware.IMiddlewareHelper;
import org.arfna.method.password.middleware.MiddlewareHelperV1;

public enum EVersion {

    V1(new DummyApiV1(), new MutateSubscriberApiV1(), new MutatePostApiV1(), new GetBlogApiV1(), new ImageIdApiV1(),
            new SubscriberCookieApiV1(), new MiddlewareHelperV1(), new DatabaseUtil(), new S3Util(), new PasswordHelperV1());

    private IDummyApi dummyAPI;
    private IMutateSubscriberApi mutateSubscriberApi;
    private IMutatePostApi mutatePostApi;
    private IGetBlogApi getBlogApi;
    private IImageIdApi imageIdApi;
    private ISubscriberCookieApi subscriberCookieApi;
    private IMiddlewareHelper middlewareHelper;
    private DatabaseUtil databaseUtil;
    private S3Util s3Util;
    private IPasswordHelper passwordHelper;

    EVersion(IDummyApi dummyAPI, IMutateSubscriberApi mutateSubscriberApi, IMutatePostApi mutatePostApi, IGetBlogApi getBlogApi, IImageIdApi imageIdApi,
             ISubscriberCookieApi subscriberCookieApi, IMiddlewareHelper middlewareHelper, DatabaseUtil databaseUtil, S3Util s3Util, IPasswordHelper passwordHelper) {
        this.dummyAPI = dummyAPI;
        this.mutateSubscriberApi = mutateSubscriberApi;
        this.mutatePostApi = mutatePostApi;
        this.getBlogApi = getBlogApi;
        this.imageIdApi = imageIdApi;
        this.subscriberCookieApi = subscriberCookieApi;
        this.middlewareHelper = middlewareHelper;
        this.databaseUtil = databaseUtil;
        this.s3Util = s3Util;
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

    public ISubscriberCookieApi getSubscriberCookieApi() {
        return subscriberCookieApi;
    }

    public IImageIdApi getImageIdApi() {
        return imageIdApi;
    }

    public S3Util getS3Util() {
        return s3Util;
    }
}
