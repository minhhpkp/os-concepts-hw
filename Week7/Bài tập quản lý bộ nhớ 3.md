# Bài tập Quản lý Bộ nhớ 3

## Bài 8.20

Kích cỡ trang là 1 KB = 1024 Byte. Giả sử một đơn vị bộ nhớ là 1 Byte, ta có kết quả như sau:

| Logical address | Page number = Logical address ÷ Page size | Offset = Logical address mod Page size |
| --------------- | ----------------------------------------- | -------------------------------------- |
| 3085            | 3                                         | 13                                     |
| 42095           | 41                                        | 111                                    |
| 215201          | 210                                       | 161                                    |
| 650000          | 634                                       | 784                                    |
| 2000001         | 1953                                      | 129                                    |

## Bài 8.23

a. Kích cỡ cần thiết cho không gian địa chỉ logic = số trang $\times$ kích cỡ trang = 256 $\times$ 4 KB = 1024 KB = 1048576 bit.

b. Kích cỡ cần thiết cho không gian địa chỉ vật lý = số frame $\times$ kích cỡ frame tối thiểu = số frame $\times$ kích cỡ trang = 64 $\times$ 4 KB = 256 KB = 262144 bit.
