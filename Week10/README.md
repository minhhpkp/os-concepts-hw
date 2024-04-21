# Bài tập về nhà tuần 10

## Bài 1

### phần a

Kích thước FAT $=$ số khối $\times$ số bit cần dùng để biểu diễn giá trị số hiệu khối.  
Số khối $=$ kích thước ổ đĩa $\div$ kích thước khối $= 128\ GB \div 8\ KB = 2^{27}\ KB \div 2^3\ KB = 2^{24}$.  
Số bit cần dùng để biểu diễn giá trị số hiệu khối $=$ số bit cần dùng để biểu diễn $2^{24}$ giá trị $= 24\ bit$.
Kích thước FAT $= 2^{24} \times 24\ bit = 48\ MB$.

### phần b

#### phần b.i

Kích thước lớn nhất của tập tin $=$ Kích thước lớn nhất lưu được bằng 12 khối trực tiếp $+$ kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 1 $+$ kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 2 $+$ kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 3.  
**Kích thước lớn nhất lưu được bằng 12 khối trực tiếp** $=$ số khối trực tiếp $\times$ kích thước khối $= 12 \times 4\ KB = 48\ KB$.  
Kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 1 $=$ số địa chỉ khối chứa được trong một khối chỉ mục $\times$ kích thước khối.  
Số địa chỉ khối chứa được trong một khối chỉ mục $=$ kích cỡ khối $\div$ lượng bộ nhớ cần để chứa giá trị số hiệu khối $= 4\ KB \div 4\ B = 2^{10}$.  
**Kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 1** $= 2^{10} \times 4\ KB = 4\ MB$.  
**Kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 2** $=$ số khối chỉ mục được trỏ tới mục bởi khối gián tiếp cấp 2 $\times$ kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 1 $=$ số địa chỉ khối chứa được trong một khối chỉ mục $\times$ kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 1 $= 2^{10} \times 4\ MB = 4\ GB$.  
**Kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 3** $=$ Số khối gián tiếp cấp 2 trỏ tới bởi khối gián tiếp cấp 3 $\times$ Kích thước lớn nhất lưu được bằng 1 khối gián tiếp cấp 2 $= 2^{10} \times 4\ GB = 4\ TB$.  
**Kích thước lớn nhất của tập tin** $= 48\ KB + 4\ MB + 4\ GB + 4\ TB$.

#### phần b.ii

Số khối cần để chứa tập tin này $=$ số khối trực tiếp $+$ (1 khối gián tiếp cấp 1 $+$ số khối dữ liệu trở đến bởi khối gián tiếp cấp 1) $+$ (1 khối gián tiếp cấp 2 $+$ số khối gián tiếp cấp 1 trỏ đến bởi khối gián tiếp cấp 2 $+$ số khối dữ liệu trỏ đến bởi các khối gián tiếp đó) $+$ (1 khối gián tiếp cấp 3 $+$ số khối gián tiếp cấp 2 trỏ đến bởi khối gián tiếp cấp 3 $+$ tổng số khối gián tiếp cấp 1 trỏ đến bởi các khối cấp 2 đó + tổng số khối dữ liệu trỏ đến bởi các khối cấp 1 đó) $= 12 + (1 + 2^{10}) + (1 + 2^{10} + 2^{10} \times 2^{10}) + (1 + 2^{10} + 2^{10} \times 2^{10} + 2^{10} \times 2^{10} \times 2^{10}) = 1,075,842,063$ khối.

## Bài 2

Giả định đẩy đủ: FCB, directory structure và index blocks đã được load vào bộ nhớ trong.

### 1. Thêm một khối vào đầu tập tin

#### 1.1. Liên tục

Các thao tác cần thiết để thêm 1 khối vào đầu tập tin là:

1. Thực hiện **2** thao tác trên ổ đĩa trên lần lượt từ khối thứ 100 đến khối thứ 1: với khối thứ i, (1) đọc khối thứ i và (2) viết giá trị vừa đọc lên khối i + 1.
2. Thực hiện viết giá trị khối mới vào khối ở đầu tập tin.
3. Chỉnh sửa giá trị _start_ và _length_ tại entry của file trên directory structure đã load trên bộ nhớ trong, không cần truy cập ổ đĩa.

Số thao tác truy cập ổ đĩa $= 2 \times 100 + 1 = 201$.

#### 1.2. Danh sách liên kết

Các thao tác cần thiết:

1. Viết giá trị khối mới vào một khối được cấp phát bởi ổ đĩa, khối này trỏ đến khối đầu tiên của tệp tin.
2. Chỉnh sửa giá trị _start_ tại entry của file trên directory structure.

Số thao tác truy cập ổ đĩa $= 1$.

#### 1.3. Chỉ mục

Giả sử khối chỉ mục của tệp tin vẫn còn chỗ trống, khi đó cần một thao tác viết vào khối chỉ mục.

Số thao tác truy cập ổ đĩa $= 1$.

### 2. Thêm một khối vào cuối tập tin

#### 2.1. Liên tục

Các thao tác cần thiết:

1. Viết giá trị khối mới vào khối ngay sau khối cuối cùng của tập tin.
2. Chỉnh sửa giá trị _length_ của entry của file trên directory structure.

Số thao tác truy cập ổ đĩa $= 1$.

#### 2.2. Danh sách liên kết

Các thao tác cần thiết:

1. Viết giá trị khối mới vào khối được cấp phát bởi hệ điều hành.
2. Một thao tác viết để chỉnh sửa con trỏ ở khối cuối cùng cũ của tập tin để trỏ đến khối cuối cùng mới.
3. Chỉnh sửa giá trị _end_ tại entry của file trên directory structure.

Số thao tác truy cập ổ đĩa $= 2$.

#### 2.3. Chỉ mục

Tương tự như thêm vào đầu tập tin, cần $1$ thao tác truy cập ổ đĩa để cập nhật khối chỉ mục.

### 3. Bỏ một khối đầu tập tin

#### 3.1. Liên tục

Không cần thao tác gì ở ổ đĩa, chỉ cần chỉnh sửa giá trị _start_ và _length_ tại entry của file trên directory structure.

#### 3.2. Danh sách liên kết

Cần $1$ thao tác trên ổ đĩa để biết địa chỉ khối thứ 2 của tập tin và một thao tác trên bộ nhớ trong để cập nhất giá trị _start_ tại entry của file trên directory structure.

#### 3.3. Chỉ mục

Cần $1$ thao tác truy cập ổ đĩa để cập nhật khối chỉ mục.

### 4. Bỏ một khối cuối tập tin

#### 4.1. Liên tục

Không cần thao tác gì trên ổ đĩa, chỉ cần chỉnh sửa giá trị _length_ tại entry của file trên directory structure.

#### 4.2. Danh sách liên kết

Các thao tác cần thiết:

1. Thực hiện 98 thao tác đọc trên ổ đĩa để biết được khối gần cuối.
2. Một thao tác viết để chỉnh sửa con trỏ của khối gần cuối (khối cuối mới).
3. Sửa giá trị _end_ tại entry của file trên directory structure.

Số thao tác truy cập ổ đĩa $= 99$.

#### 4.4. Chỉ mục

Cần $1$ thao tác truy cập ổ đĩa để cập nhật khối chỉ mục.
