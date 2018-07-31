package com.roshine.lstypechoblog.http;

import android.text.TextUtils;

import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.interceptor.HeaderInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.timroes.axmlrpc.Call;
import de.timroes.axmlrpc.ResponseParser;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.serializer.SerializerHandler;
import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Roshine
 * @date 2017/8/17 15:10
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LsRetrofitManage {
    private static LsRetrofitManage instance;

    private String baseUrl;

    private Map<String, Object> headerMaps = new HashMap<>();

    private boolean isShowLog = true;
    private boolean cache = false;
    private boolean saveCookie = true;

    private String cachePath;
    private long cacheMaxSize;

    private long readTimeout = Constants.NormalConstants.TIME_OUT;
    private long writeTimeout = Constants.NormalConstants.TIME_OUT;
    private long connectTimeout = Constants.NormalConstants.TIME_OUT;

//    private SSLUtils.SSLParams sslParams;

    private OkHttpClient okClient;
    private String method;
    private Object[] parameters;
    private ErrorCallBack errorCallBack;

    public static LsRetrofitManage getInstance() {
        if (instance == null) {
            synchronized (LsRetrofitManage.class) {
                if (instance == null) {
                    instance = new LsRetrofitManage();
                }
            }

        }
        return instance;
    }

    public LsRetrofitManage baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public LsRetrofitManage addHeaders(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
        return this;
    }

    public LsRetrofitManage log(boolean isShowLog) {
        this.isShowLog = isShowLog;
        return this;
    }

    public LsRetrofitManage cache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public LsRetrofitManage saveCookie(boolean saveCookie) {
        this.saveCookie = saveCookie;
        return this;
    }

    public LsRetrofitManage cachePath(String cachePath, long maxSize) {
        this.cachePath = cachePath;
        this.cacheMaxSize = maxSize;
        return this;
    }

    public LsRetrofitManage readTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public LsRetrofitManage writeTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public LsRetrofitManage connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }
    public LsRetrofitManage method(String methodName){
        this.method = methodName;
        return this;
    }

    public LsRetrofitManage parameters(Object[] parameters){
        this.parameters = parameters;
        return this;
    }
    public LsRetrofitManage parameters(List<Object> parameters){
        if (parameters != null) {
            this.parameters = parameters.toArray();
        }
        return this;
    }

    public LsRetrofitManage errorCallBack(ErrorCallBack errorCallBack){
        this.errorCallBack = errorCallBack;
        return this;
    }

//    /**
//     * 信任所有证书,不安全有风险
//     *
//     * @return
//     */
//    public LsRetrofitManage sslSocketFactory() {
//        sslParams = SSLUtils.getSslSocketFactory();
//        return this;
//    }
//
//    /**
//     * 使用预埋证书，校验服务端证书（自签名证书）
//     *
//     * @param certificates
//     * @return
//     */
//    public LsRetrofitManage sslSocketFactory(InputStream... certificates) {
//        sslParams = SSLUtils.getSslSocketFactory(certificates);
//        return this;
//    }
//
//    /**
//     * 使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//     *
//     * @param bksFile
//     * @param password
//     * @param certificates
//     * @return
//     */
//    public LsRetrofitManage sslSocketFactory(InputStream bksFile, String password, InputStream... certificates) {
//        sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
//        return this;
//    }

    public LsRetrofitManage client(OkHttpClient okClient) {
        this.okClient = okClient;
        return this;
    }

    /**
     * 使用自己自定义参数创建请求
     * @return
     */
    public Flowable<Response<ResponseBody>> getService() {
        Flowable<Response<ResponseBody>> result = null;
        try {
            if(method != null ){
                Call call = createCall(method, parameters);
                String xml = null;
                try {
                    xml = call.getXML(false);
                } catch (XMLRPCException e) {
                    e.printStackTrace();
                    if (errorCallBack != null) {
                        errorCallBack.error(e);
                        errorCallBack = null;
                    }
                }
                RxService rxService = getSingleRetrofitBuilder().build().create(RxService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse(Constants.NormalConstants.CONTENT_TYPE), xml);
                result = rxService.getResult(requestBody);
            }else{
                throw new IllegalArgumentException("the method must be not null");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            if (errorCallBack != null) {
                errorCallBack.error(e);
                errorCallBack = null;
            }
        }
        return result;
    }

    private Call createCall(String method, Object[] params) {
        SerializerHandler serializerHandler = new SerializerHandler(0);
        return new Call(serializerHandler, method, params);
    }


    /**
     * 单个RetrofitBuilder
     *
     * @return
     */
    public Retrofit.Builder getSingleRetrofitBuilder() {
        Retrofit.Builder singleRetrofitBuilder = new Retrofit.Builder();
        singleRetrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient == null ? getSingleOkHttpBuilder().build() : okClient);

        try {
            if (baseUrl != null && !TextUtils.isEmpty(baseUrl)) {
                if(baseUrl.endsWith("/")){
                    singleRetrofitBuilder.baseUrl(baseUrl);
                }else{
                    singleRetrofitBuilder.baseUrl(baseUrl + "/");
                }
            }else{
                if (errorCallBack != null) {
                    errorCallBack.error(new RxUrlException("the base url was null!"));
                    errorCallBack = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (errorCallBack != null) {
                errorCallBack.error(new RxUrlException("the base url was error!"));
                errorCallBack = null;
            }
        }
        return singleRetrofitBuilder;
    }

    /**
     * 获取单个 OkHttpClient.Builder
     *
     * @return
     */
    public OkHttpClient.Builder getSingleOkHttpBuilder() {

        OkHttpClient.Builder singleOkHttpBuilder = new OkHttpClient.Builder();

        singleOkHttpBuilder.retryOnConnectionFailure(true);

        singleOkHttpBuilder.addInterceptor(new HeaderInterceptor(headerMaps));

//        if (cache) {
//            CacheInterceptor cacheInterceptor = new CacheInterceptor();
//            Cache cache;
//            if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
//                cache = new Cache(new File(cachePath), cacheMaxSize);
//            } else {
//                cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath() + "/rxHttpCacheData")
//                        , 1024 * 1024 * 100);
//            }
//            singleOkHttpBuilder.addInterceptor(cacheInterceptor)
//                    .addNetworkInterceptor(cacheInterceptor)
//                    .cache(cache);
//        }
//        if (isShowLog) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                @Override
//                public void log(String message) {
//                    Log.e("RxHttpUtils", message);
//
//                }
//            });
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            singleOkHttpBuilder.addInterceptor(loggingInterceptor);
//        }
//
//        if (saveCookie) {
//            singleOkHttpBuilder
//                    .addInterceptor(new AddCookiesInterceptor())
//                    .addInterceptor(new ReceivedCookiesInterceptor());
//        }

        singleOkHttpBuilder.readTimeout(readTimeout > 0 ? readTimeout : 10, TimeUnit.SECONDS);

        singleOkHttpBuilder.writeTimeout(writeTimeout > 0 ? writeTimeout : 10, TimeUnit.SECONDS);

        singleOkHttpBuilder.connectTimeout(connectTimeout > 0 ? connectTimeout : 10, TimeUnit.SECONDS);

//        if (sslParams != null) {
//            singleOkHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//        }

        return singleOkHttpBuilder;
    }

//    public void toSubscribe(Observable ob, final LifeSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
//        //数据预处理
//        Observable.Transformer<Object, Object> result = handleResult(event,lifecycleSubject);
//        Observable observable = ob.compose(result)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        //显示Dialog和一些其他操作
//                        subscriber.showProgressDialog();
//                    }
//                });
//        RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe(subscriber);
//    }
//
//
//    public <T> Observable.Transformer<T, T> bindUntilEvent(final ActivityEvent bindEvent,BehaviorSubject<ActivityEvent> lifeSubject) {
//        //被监视的Observable
//        final Observable<ActivityEvent> observable = lifeSubject.takeUntil(new Func1<ActivityEvent, Boolean>() {
//            @Override
//            public Boolean call(ActivityEvent event) {
//                return event.equals(bindEvent);
//            }
//        });
//
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> sourceOb) {
//                return sourceOb.takeUntil(observable);
//            }
//        };
//    }
//    /**
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<Object, Object> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
//        return new Observable.Transformer<HttpResult<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<HttpResult<T>> tObservable) {
//                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
//                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
//                            @Override
//                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
//                                return activityLifeCycleEvent.equals(event);
//                            }
//                        });
//                return tObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(HttpResult<T> result) {
//                        if (result.getCount() != 0) {
//                            return createData(result.getSubjects());
//                        } else {
//                            return Observable.error(new ApiException(result.getCount()));
//                        }
//                    }
//                }).takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
}
