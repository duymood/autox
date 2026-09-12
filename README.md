# Auto X On Trade (Fabric Mod - MC 1.21.11)

Mod này làm một việc duy nhất: **ngay khi bạn mở GUI giao dịch của dân làng
(Villager Trade GUI), mod sẽ tự động giả lập một lần nhấn phím `X`** trên
màn hình đó.

## Vì sao chỉ giả lập trên chính màn hình GUI?

Mod gọi thẳng `screen.keyPressed(GLFW_KEY_X, 0, 0)` — đúng như khi bạn tự tay
bấm X lúc GUI đang mở. Cách này an toàn, không đụng vào driver bàn phím hay
gửi input giả ra ngoài hệ thống, và hoạt động y hệt hành vi bạn mô tả.

> Lưu ý: nếu phím `X` chưa được gán cho hành động nào trong bản đồ phím của
> bạn, việc "bấm X" sẽ không có tác dụng gì (vì `MerchantScreen` gốc của
> Minecraft không xử lý phím X cho mục đích cụ thể nào). Nếu bạn có mod khác
> gán hành động riêng cho phím X trong màn hình này (ví dụ khóa công thức,
> auto-trade của mod khác, v.v.), mod này sẽ kích hoạt đúng hành động đó.
> Nếu bạn muốn mod này **tự làm luôn một hành động cụ thể** (ví dụ: tự động
> chọn công thức đầu tiên, tự trade, tự đóng GUI...) thay vì chỉ giả lập phím,
> nói rõ hành động đó để mình chỉnh code trực tiếp cho chắc ăn.

## Yêu cầu để build

- **JDK 21** (bắt buộc cho MC 1.21.11)
- Kết nối mạng (Gradle cần tải Minecraft, mappings, Fabric Loader, Fabric API)
- IntelliJ IDEA (khuyến nghị) hoặc VS Code + Java extension

## Cách build

```bash
# Tại thư mục gốc project (nơi có build.gradle)
./gradlew build
```

(Trên Windows dùng `gradlew.bat build` — nhưng cần có gradle wrapper, xem bên dưới)

File `.jar` kết quả nằm ở:
```
build/libs/auto-x-on-trade-1.0.0.jar
```

## Cách 2: Build bằng GitHub (không cần cài gì trên máy)

Project đã kèm sẵn file `.github/workflows/build.yml` — GitHub sẽ tự cài
Java 21, tự cài Gradle, tự build, và cho bạn tải file `.jar` về. Làm theo
các bước sau:

### Bước 1: Tạo tài khoản GitHub (nếu chưa có)
Vào https://github.com/signup, tạo tài khoản miễn phí.

### Bước 2: Tạo repository mới
1. Đăng nhập GitHub → bấm nút **+** góc trên bên phải → **New repository**.
2. Đặt tên bất kỳ, ví dụ `autox-mod`.
3. Để **Public** hoặc **Private** đều được (Actions miễn phí cho cả hai với
   tài khoản cá nhân).
4. **Không** tick "Add a README file" (vì mình đã có sẵn).
5. Bấm **Create repository**.

### Bước 3: Tải code lên GitHub — cách dễ nhất là kéo-thả trên web

1. Giải nén file `autox-mod.zip` mình gửi ra một thư mục trên máy.
2. Ở trang repo vừa tạo, GitHub sẽ hiện dòng chữ
   *"uploading an existing file"* — bấm vào đó
   (hoặc vào **Add file → Upload files**).
3. Mở thư mục đã giải nén, **chọn tất cả file và thư mục con** bên trong
   (bao gồm cả thư mục `.github` — nếu Windows Explorer ẩn thư mục bắt đầu
   bằng dấu chấm, bật "Show hidden items" trước), kéo thả toàn bộ vào
   khung upload của GitHub.
4. Cuộn xuống, bấm **Commit changes**.

> Nếu bạn quen dùng Git command line, cách nhanh hơn:
> ```bash
> cd autox-mod
> git init
> git add .
> git commit -m "Initial commit"
> git branch -M main
> git remote add origin https://github.com/<ten-ban>/autox-mod.git
> git push -u origin main
> ```

### Bước 4: Xem GitHub build

1. Vào tab **Actions** trên trang repo (thanh menu ngang, cạnh "Code",
   "Issues"...).
2. Bạn sẽ thấy một workflow tên **"Build Fabric Mod"** đang chạy (chấm
   vàng xoay) — nó tự chạy ngay sau khi bạn commit code ở Bước 3.
3. Đợi khoảng 3–8 phút. Nếu chấm chuyển thành **dấu tích xanh** ✅ là build
   thành công. Nếu là **dấu X đỏ** ❌, bấm vào để xem log lỗi rồi gửi lại
   cho mình đoạn lỗi đó.

### Bước 5: Tải file .jar về

1. Bấm vào lần chạy build thành công (dòng có dấu tích xanh).
2. Cuộn xuống phần **Artifacts** ở cuối trang.
3. Bấm vào **auto-x-on-trade-jar** để tải về — đây là file `.zip` chứa
   file `.jar` bên trong, giải nén ra là lấy được file mod.
4. Copy file `.jar` đó vào thư mục `mods` của Minecraft Fabric (1.21.11) là
   xong.

### Muốn build lại sau khi sửa code?

Chỉ cần sửa file trực tiếp trên GitHub (bấm vào file → nút bút chì ✏️ →
sửa → Commit changes), workflow sẽ tự chạy lại và ra bản `.jar` mới trong
tab Actions.

---

## Thiếu Gradle Wrapper

Project này chưa kèm sẵn thư mục `gradle/wrapper/` (file `.jar` nhị phân của
Gradle Wrapper) vì môi trường tạo file không có mạng để tải. Bạn cần chạy:

```bash
gradle wrapper --gradle-version 8.10
```

(cần cài Gradle command-line có sẵn 1 lần để sinh wrapper — sau đó dùng
`./gradlew` như bình thường). Hoặc mở thẳng project bằng IntelliJ IDEA:
IntelliJ sẽ tự nhận `build.gradle` và tải wrapper giúp bạn.

## Cài mod vào game

1. Cài **Fabric Loader** cho Minecraft 1.21.11 (từ fabricmc.net).
2. Cài **Fabric API** bản `0.141.3+1.21.11` (hoặc mới hơn) vào thư mục `mods`.
3. Copy file `auto-x-on-trade-1.0.0.jar` vừa build vào thư mục `mods`.
4. Mở Minecraft bằng profile Fabric, vào game, mở GUI giao dịch dân làng để
   kiểm tra (xem log `[AutoX] Da tu dong bam phim X...` trong `latest.log`
   để xác nhận nó chạy).

## Bật/tắt nhanh khi test

Trong file `AutoXModClient.java`, biến `enabled` mặc định là `true`.
Đổi thành `false` rồi build lại nếu muốn tắt tạm thời.

## Các version đã dùng (kiểm tra lại tại https://fabricmc.net/develop nếu build lỗi)

| Thành phần     | Version              |
|----------------|----------------------|
| Minecraft      | 1.21.11              |
| Yarn mappings  | 1.21.11+build.1      |
| Fabric Loader  | 0.16.10              |
| Fabric API     | 0.141.3+1.21.11      |
| Fabric Loom    | 1.9-SNAPSHOT         |
| Java           | 21                   |
