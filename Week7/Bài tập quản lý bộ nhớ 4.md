# Bài tập Quản lý Bộ nhớ 4

## Bài 1

Kích thước trang $=$ Kích thước frame $= 2\ KB = 2 \times 1024 \times 8\ bit = 16384\ bit$.

a. Số entry trên bảng phân trang một cấp thông thường $=$ số trang $=$ kích thước bộ nhớ ảo $\div$ kích thước trang = $2^{21} \div 16384 = 128$.

b.
Số entry trên bảng phân trang nghịch đảo $=$ số frame $=$ kích thước bộ nhớ vật lý $\div$ kích thước frame $= 2^{16} \div 16384 = 4$.

## Bài 2

a.  
Kích thước bảng phân trang $=$ số trang $\times$ số bit sử dụng để biểu diễn một giá trị frame number.  
Số trang $=$ kích thước bộ nhớ ảo $\div$ kích thước trang $= 1\ GB \div 4\ KB = 2^{20}\ KB \div 4\ KB = 2^{18}$.  
Số bit cần thiết để biểu diễn một giá trị frame number $= log_2(số\ frame) = log_2(256) = 8$ bit.  
Vậy kích thước bảng phân trang $= 2^{18} \times 8\ bit = 2^{21}\ bit = 256\ KB$.  
Kích thước bộ nhớ trong $=$ số frame $\times$ kích thước frame $= 256 \times 4\ KB = 1024\ KB$.  
Vì kích thước bộ nhớ trong $>$ kích thước bảng phân trang nên bộ nhớ trong có thể chứa được toàn bộ bảng phân trang.

b.  
Kích thước bảng phân trang nghịch đảo $=$ số frame $\times$ (số bit cần thiết để biểu diễn một giá trị pid $+$ số bit cần thiết để biểu diễn một giá trị page number).  
Vì một tiến trình để có pid thì cần được load lên RAM, và mỗi tiến trình trên RAM chiếm tối thiểu 1 frame nên số tiến trình tối đa hay số giá trị pid tối đa là số frame $= 256 = 2^8$. Như vậy cần 8 bit để biểu diễn pid.  
Số trang $= 2^{18}$ nên cần 18 bit để biểu diễn page number.  
Vậy kích thước bảng phân trang nghịch đảo $= 256 \times (8 + 18) = 6656\ bit$.  
Kích thước trang $= 4\ KB = 4 \times 1024 \times 8\ bit = 32768\ bit$.  
Vì kích thước trang $>$ kích thước bảng phân trang nghịch đảo nên một trang có thể chứa đủ bảng phân trang nghịch đảo.
