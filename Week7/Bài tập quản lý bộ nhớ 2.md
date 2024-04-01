# Bài tập Quản lý Bộ nhớ 2

a. Để truy cập một địa chỉ bộ nhớ khi sử dụng phân trang, hệ thống đầu tiên phải truy cập vào bộ nhớ để lấy được bảng phân trang và số frame mong muốn, rồi truy cập vào địa chỉ bộ nhớ mong muốn. Nếu giả sử một lần tìm kiếm trên bảng phân trang chỉ cần 1 lần truy cập vào bộ nhớ, thì tổng cộng ta cần 2 lần truy cập vào bộ nhớ tức 100 nano giây (ns).

b. Trong 75% thời gian, ta tìm được số trang mong muốn trong TLB mất 2 ns và 1 lần truy cập vào bộ nhớ để truy cập giá trị tại địa chỉ tương ứng mất 50 ns. Trong 25% còn lại ta cần 100 ns (như giả thích ở phần a). Do đó:
effective memory reference time $= 0.75 \times (2 + 50) + 0.25 \times 100 = 64$ ns.
