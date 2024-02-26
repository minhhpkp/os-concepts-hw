# Bài tập về nhà tuần 2

## Fork

Code: [myfork.c](/Week2/Fork/myfork.c).

Chạy chương trình bằng lệnh:

    gcc myfork.c -o myfork
    ./myfork

Kết quả chạy:

![myfork run result](/Week2/Images/Screenshot%20from%202024-02-24%2013-40-39.png)

Giải thích kết quả:

Trước khi chạy hàm fork(), chương trình _myfork_ in ra pid (process identifier) của nó và của cha của nó, trong lần chạy này lần lượt là `15512` và `15304`. Cha của chương trình _myfork_ là _bash_, và ta có thể xác nhận điều này nhờ lệnh `ps`. Như trên hình, _bash_ có pid là 15304, trùng với pid của cha của _myfork_.

Sau khi chạy hàm fork(), một tiến trình con của _myfork_ được tạo ra và có pid là `15513`. Tiến trình con này là một bản sao của không gian địa chỉ (address space) của tiến trình cha (pid 15512). Cả hai tiến trình cha và con tiếp tục chạy từ lệnh tiếp theo sau fork(), với một điểm khác biệt: hàm fork() trả về 0 đối với tiến trình con và trả về một giá trị khác 0 (chính là pid của tiến trình con vừa tạo) đối với tiến trình cha. Do đó, sẽ có 2 dòng "After fork()..." được tạo ra do dòng `printf("After fork()...")` được thực hiện một lần ở mỗi tiến trình cha và con. Ở lệnh if tiếp theo, tiến trình con sẽ chạy khối lệnh ở nhánh điều kiện `pid == 0`, do đó sẽ chạy `value += 15` và in ra `value = 20`. Trong khi đó, tiến trình cha sẽ chạy lệnh ở nhánh điều kiện `pid > 0`, ở đó, có lệnh `wait(NULL)` có ý nghĩa là đợi cho tiến trình con chạy xong rồi mới tiếp tục. Vì 2 tiến trình cha con là độc lập nên việc tăng biến value lên 15 ở tiến trình con không ảnh hưởng đến tiến trình cha. Vì vậy, tiến trình cha in ra `value = 5`.

## Project 1

Code: [osh.c](/Week2/Project1/osh.c).

Chạy chương trình bằng lệnh:

    gcc osh.c -o osh
    ./osh

## Project 2

### Part 1:

Code: [tasklist.c](/Week2/Project2/Part1/tasklist.c)

### Part 2:

Code: [dfsptree.c](/Week2/Project2/Part2/dfsptree.c)
