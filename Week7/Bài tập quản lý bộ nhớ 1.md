# Bài tập Quản lý Bộ nhớ 1

## Bài 1 trên slide

a. Giả sử mỗi đơn vị bộ nhớ là 1 byte, và kích thước trang bằng kích thước frame.  
Với địa chỉ ảo $9EF_{16} = 2543_{10}$, page number $= 2543_{10} \div 256 = 9$, page offset $= 2543_{10}\ mod\ 256 = 239$. Frame number tương ứng với page number 9 là 0. Do đó, địa chỉ bộ nhớ vật lý tương ứng vơi địa chỉ ảo 9EF là $0 \cdot 256 + 239 = 239_{10} = EF_{16}$.

Tương tự với 2 địa chỉ ảo còn lại, ta có:

| Logical address | Page number = Logical address ÷ Page size | Page offset = Logical address mod Page size | Frame number | Physical address = Frame number \* Frame size + Page offset     |
| --------------- | ----------------------------------------- | ------------------------------------------- | ------------ | --------------------------------------------------------------- |
| 700             | 7                                         | 0                                           | -            | Không có địa chỉ vật lý tương ứng vì page chưa được tải vào RAM |
| 0FF             | 0                                         | 255                                         | -            | Không có địa chỉ vật lý tương ứng vì page chưa được tải vào RAM |

b. Số đơn vị bộ nhớ tối đa khi sử dụng 12 bit để đánh địa chỉ là $2^{12}$.  
Dung lượng bộ nhớ chính $=$ số đơn vị bộ nhớ $\times$ kích thước mỗi đơn vị bộ nhớ $= 2^{12} \times 4\ B = 2^{14}\ B = 16384\ B$.  
Số lượng frame $=$ Dung lượng bộ nhớ chính $\div$ Kích thước frame $= 16384 \div 256 = 64$.

## Bài 2

Kết quả thực thi các chiến lược phân mảnh first-fit, best-fit và worst-fit được ghi trên bảng sau:

| Ban đầu | First fit | Best fit | Worst fit |
| :-----: | :-------: | :------: | :-------: |
|   300   |  **115**  |   300    |    300    |
|   600   |    185    | **500**  |  **358**  |
|   350   |  **500**  |   100    |    242    |
|   200   |    100    |   350    |  **200**  |
|   750   |  **200**  | **200**  |    150    |
|   125   |    150    | **358**  |    200    |
|         |    200    | **375**  |  **115**  |
|         |  **358**  |    17    |  **500**  |
|         |  **375**  | **115**  |    135    |
|         |    17     |    10    |    125    |
|         |    125    |          |           |

Trong đó, các ô được bôi đậm là những vùng bộ nhớ được cấp phát cho các tiến trình.

Chiến lược first-fit và best-fit có thể cấp phát bộ nhớ cho tất cả các tiến trình và có hiệu quả cao nhất. Chiến lược worst-fit không cấp phát được bộ nhớ cho tiến trình cuối cùng nên có hiệu quả thấp hơn.

## Bài 3

Xét địa chỉ logic <0, 430>, trong đó segment number = 0 và offset = 430. từ bảng phân đoạn ta có base = 219 và length = 600. Vì offset < length nên offset này là hợp lệ và ta có thể tính được địa chỉ vật lý = offset + base = 649.

Xét địa chỉ logic <2, 500>, trong đó segment number = 2 và offset = 500. Từ bảng phân đoạn ta có length = 100. Vì offset > length nên đây là địa chỉ không hợp lệ.

Các địa chỉ còn lại làm tương tự, ta có bảng kết quả như sau:

| Logical address  |          | Base | Length | Offset < Length ? | Physical address = Offset + Base |
| ---------------- | -------- | ---- | ------ | ----------------- | -------------------------------- |
| _Segment number_ | _Offset_ |      |        |                   |                                  |
| 0                | 430      | 219  | 600    | Yes               | 649                              |
| 1                | 10       | 2300 | 14     | Yes               | 2310                             |
| 2                | 500      | 90   | 100    | No                | addressing error                 |
| 3                | 400      | 1327 | 580    | Yes               | 1727                             |
| 4                | 112      | 1952 | 96     | No                | addressing error                 |
