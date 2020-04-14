# 说明
本项目基于以下项目进行修改，原项目是使用在Android项目上的。

[原项目戳这](https://github.com/hongyangAndroid/okhttputils)

# 日志
框架内引用了SLF4J的API，在外层应用中使用的时候可以使用具体的实现类。

# 用法

### 初始化

默认情况下，将直接使用okhttp默认的配置生成OkhttpClient，如果你有任何配置，记得在Application中调用`initClient`方法进行设置。

````
 OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                  .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                  .readTimeout(10000L, TimeUnit.MILLISECONDS)
                  //其他配置
                 .build();
                 
        OkHttpUtils.initClient(okHttpClient);

````

### 对于Cookie(包含Session)
对于cookie一样，直接通过cookiejar方法配置，参考上面的配置过程。
````
CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
OkHttpClient okHttpClient = new OkHttpClient.Builder()
          .cookieJar(cookieJar)
          //其他配置
         .build();
                 
OkHttpUtils.initClient(okHttpClient);
````
目前项目中包含：

- SerializableHttpCookie //持久化cookie
- MemoryCookieStore //cookie信息存在内存中

### HTTPS
框架中提供了一个类`HttpsUtils`
- 设置可访问所有的https网站
````
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
         //其他配置
         .build();
OkHttpUtils.initClient(okHttpClient);
````
- 设置具体的证书
````
HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(证书的inputstream, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager))
         //其他配置
         .build();
OkHttpUtils.initClient(okHttpClient);
````
- 双向认证
````
HttpsUtils.getSslSocketFactory(
	证书的inputstream, 
	本地证书的inputstream, 
	本地证书的密码)
````
同样的，框架中只是提供了几个实现类，你可以自行实现SSLSocketFactory，传入sslSocketFactory即可。

## 用法示例

### **GET请求**
````
String url = "http://www.csdn.net/";
OkHttpUtils
    .get()
    .url(url)
    .addParams("username", "hyman")
    .addParams("password", "123")
    .build()
    .execute(new StringCallback(){
	            @Override
	            public void onError(Request request, Exception e){
	                
	            }
	
	            @Override
	            public void onResponse(String response){
	
	            }
	        });
````

### **POST请求**
````
 OkHttpUtils
    .post()
    .url(url)
    .addParams("username", "hyman")
    .addParams("password", "123")
    .build()
    .execute(callback);
````

### **Post JSON**
````
  OkHttpUtils
    .postString()
    .url(url)
    .content(new Gson().toJson(new User("zhy", "123")))
     .mediaType(MediaType.parse("application/json; charset=utf-8"))
    .build()
    .execute(new MyStringCallback());
````
传递JSON的时候，不要通过addHeader去设置contentType，而使用.mediaType(MediaType.parse("application/json; charset=utf-8"))。

### **上传文件**
````
 OkHttpUtils
	.postFile()
	.url(url)
	.file(file)
	.build()
	.execute(new MyStringCallback());
````

### **Post表单形式上传文件**
````
OkHttpUtils.post()//
    .addFile("mFile", "messenger_01.png", file)//
    .addFile("mFile", "test1.txt", file2)//
    .url(url)
    .params(params)//
    .headers(headers)//
    .build()//
    .execute(new MyStringCallback());
````
支持单个多个文件，addFile的第一个参数为文件的key，即类别表单中<input type="file" name="mFile"/>的name属性。

### **下载文件**
````
 OkHttpUtils//
	.get()//
	.url(url)//
	.build()//
	.execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar")//
	{
	    @Override
	    public void inProgress(float progress)
	    {
	        mProgressBar.setProgress((int) (100 * progress));
	    }
	
	    @Override
	    public void onError(Request request, Exception e)
	    {
	        Log.e(TAG, "onError :" + e.getMessage());
	    }
	
	    @Override
	    public void onResponse(File file)
	    {
	        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
	    }
	});
````
注意下载文件可以使用FileCallback，需要传入文件需要保存的文件夹以及文件名。

### **上传下载的进度显示**
````
new Callback<T>()
{
    //...
    @Override
    public void inProgress(float progress)
    {
       //use progress: 0 ~ 1
    }
}
````
callback回调中有inProgress 方法，直接复写即可。

### **同步请求**
````
 Response response = OkHttpUtils
    .get()//
    .url(url)//
    .tag(this)//
    .build()//
    .execute();
````
execute方法不传入callback即为同步的请求，返回Response。

### **自定义CallBack**
````
public abstract class UserCallback extends Callback<User>
{
    @Override
    public User parseNetworkResponse(Response response) throws IOException
    {
        String string = response.body().string();
        User user = new Gson().fromJson(string, User.class);
        return user;
    }
}

 OkHttpUtils
    .get()//
    .url(url)//
    .addParams("username", "hyman")//
    .addParams("password", "123")//
    .build()//
    .execute(new UserCallback()
    {
        @Override
        public void onError(Request request, Exception e)
        {
            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(User response)
        {
            mTv.setText("onResponse:" + response.username);
        }
    });
````
通过parseNetworkResponse 回调的response进行解析，该方法运行在子线程

### **HEAD、DELETE、PUT、PATCH**
````
OkHttpUtils
     .put()//also can use delete() ,head() , patch()
     .requestBody(RequestBody.create(null, "may be something"))//
     .build()//
     .execute(new MyStringCallback());
````
如果需要requestBody，例如：PUT、PATCH，自行构造进行传入。

### **根据tag取消请求**
````
 RequestCall call = OkHttpUtils.get().url(url).build();
 call.cancel();
````

### **根据tag取消请求**
````
OkHttpUtils
    .get()//
    .url(url)//
    .tag(this)//
    .build()//

@Override
protected void onDestroy()
{
    OkHttpUtils.cancelTag(this);//取消以Activity.this作为tag的请求
}
````