# CustomVolley

Đây là bộ volley tùy biến dựa trên volley và Gson chuẩn của google để tạo một số chức năng phổ biến, cần thiết mà trong quá trình làm việc với volley tôi đã gặp phải.

Có thể nó chưa được hoàn toàn _sạch sẽ_ vậy nên tôi rất hoan nghênh các bạn góp ý cho tôi

### Chức năng chính nổi bật
* Cho phép nhớ cache với tất cả các url dù website có hỗ trợ hay không hỗ trợ nhớ cache
* Cho phép đặt thời gian timeout
* Lắng nghe sự kiện một cách gọn nhẹ và thông báo lỗi một cách chính xác

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
_**Nếu bạn đang sử dụng một class Application được tùy biến vậy thì chỉ cần kế thừa từ Application đó thay vì  kế thừa android.app.Application**_

## Sử dụng
Sau khi cấu hình các thông số trên, các bạn chỉ cần download toàn bộ folder volley về và paste vào project của các bạn.

Đây là ví dụ ngắn sử dụng 

```java
	Json.init(context)
                .put("username", user)    // only use put(key, value) when method is POST
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

**put(String key, String value)** : Truyền một đối số lên, lưu ý vì volley không gọi hàm getParams với phương thức GET nên các bạn không dùng nó để truyền dữ liệu lên với phương thức GET 

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

**getGson()** phương thức static lấy đối tượng Gson. Tôi cũng đã gặp một số lỗi đại loại như [Gson Cannot make field constructor accessible](http://www.nguyenvanquan7826.com/2016/03/06/android-fix-gson-cannot-make-field-constructor-accessible/) và tôi đã tích hợp nó luôn vào đây.

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
