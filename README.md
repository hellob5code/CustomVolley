# CustomVolley

Đây là bộ volley tùy biến dựa trên volley và Gson chuẩn của google để có thể xử dụng một cách nhanh chóng và hiệu quả, đặc biệt là có thể lưu cache ngay cả với những website không cho lưu cache.

## Cấu hình trong Gradle

    dependencies {
    	compile 'com.mcxiaoke.volley:library:1.0.19'
    	compile 'com.google.code.gson:gson:2.4'
    	...
    }

## Cấu hình trong manifest

```xml
 <application
        android:name="com.nguyenvanquan7826.demo.volley.AppController"

		...
	</application>
</manifest>
```
Nếu bạn đang sử dụng một class Application được tùy biến vậy thì chỉ cần kế thừa từ Application đó thay vì  kế thừa android.app.Application

## Sử dụng
Đây là ví dụ ngắn sử dụng 


```java
	Json.init(context)
                .put("username", user)
                .put("password", password)
                .setIsCache(false)
                .setUrl("http://nguyenvanquan7826.com/login")
                .setListener(new Json.JsonListener() {
                    @Override
                    public void onFinishLoadJson(String error, String json, String tag) {
                        if (json != null) {
                            // do something when success
                        }
                        if (error != null) {
                            // do something with error
                        }
                    }
                })
                .runPost();
```

## Các hàm chính cần biết 

class **Json** cung cấp một số lệnh để thao tác như sau

**put(String key, String value)** : Truyền một đối số lên

**setMethod(int method):** Đặt phương thức gửi lên (mặc định là GET)

**setUrl(String url):** đẳt url muốn truy xuất

**setIsCache(boolean isCache):** Đặt xem có cho phép lưu cache của url hay không (mặc định là có). Với các link nhạy cảm như login thì các bạn nên đặt false cho nó. Bình thường nếu chúng ta lấy cache từ hệ thống chuẩn của volley thì chỉ những website nào cho phép nhớ cache thì chúng ta mới có và một số website của tôi không cho phép nhớ cache, tôi đã xây dựng nó và nó hoạt động rất tốt. Thời gian nhớ cache là 36 giờ, bạn có thể chỉnh sửa nó bởi biến **cacheExpired** trong class **HttpHeaderParserForCache**.

**setTimeout(int timeout):** Đặt thời gian timeout (tính theo ms, mặc định là 3s => 3000 ms). Tôi xây dựng nó vì tôi đã gặp một trường hợp server của tôi rất chậm nên tôi cần nhiều thời gian hơn để lấy nó

**init(Context context):** Hàm khởi tạo

**run():** Gửi request lên server theo phương thức đã đặt (mặc định là GET)

**runPost():** Gửi request lên server theo phương thức POST

**runPost(String tag):** Gửi request lên server theo phương thức POST và đặt TAG cho request

**run(final String tag):** Gửi request lên server theo phương thức đã đặt (mặc định là GET) và đặt TAG cho request

**cancel(String tag):** Hủy request với tag đã đặt

**setListener(JsonListener listener):** Đặt bộ lắng nghe sự kiện về. listener sẽ thực hiện viết đè hàm 
**onFinishLoadJson(String error, String json, String tag)** Nếu thành công thì json sẽ là một chuỗi khác null, error khác null tức là có lỗi.

## Một số thứ khác
Có thể bạn sẽ cần một vài chuỗi sau đây để phục vụ cho thông báo lỗi khi sử dụng volley

```xml
<string name="no_network_connection" translatable="false">No network connection found</string>
<string name="generic_server_down" translatable="false">Server down</string>
<string name="generic_server_timeout" translatable="false">Server Timeout</string>
<string name="generic_error" translatable="false">No internet</string>
<string name="no_internet" translatable="false">No internet</string>
<string name="parsing_failed" translatable="false">Parsing Failure</string>
<string name="auth_failed" translatable="false">Authentication Failure</string>
```
