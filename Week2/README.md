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

Kết quả chạy:

![simple shell](/Week2/Images/Screenshot%20from%202024-02-27%2000-15-11.png)

![simple shell](/Week2/Images/Screenshot%20from%202024-02-27%2000-15-54.png)

## Project 2

### Part 1:

Code: [tasklist.c](/Week2/Project2/Part1/tasklist.c).

Build module bằng lệnh:

    make

Thêm module vào kernel bằng lệnh:

    sudo insmod tasklist.ko

Xem kết quả thêm module trên log của kernel bằng lệnh:

    sudo dmesg | tail -500

Bỏ module khỏi kernel bằng lệnh:

    sudo rmmod tasklist

Kết quả chạy:

![tasklist](/Week2/Images/Screenshot%20from%202024-02-27%2000-18-49.png)

Kết quả chạy lệnh `sudo dmesg | tail -500`:

![tasklist](/Week2/Images/Screenshot%20from%202024-02-27%2000-20-22.png)

Kết quả chạy lệnh bỏ module:

![tasklist](/Week2/Images/Screenshot%20from%202024-02-27%2000-22-05.png)

### Part 2:

Code: [dfsptree.c](/Week2/Project2/Part2/dfsptree.c)

Build module bằng lệnh:

    make

Thêm module vào kernel bằng lệnh:

    sudo insmod dfsptree.ko

Xem kết quả thêm module trên log của kernel bằng lệnh:

    sudo dmesg | tail -500

Bỏ module khỏi kernel bằng lệnh:

    sudo rmmod dfsptree

Kết quả chạy:

![dfsptree](/Week2/Images/Screenshot%20from%202024-02-27%2000-25-29.png)

Kết quả chạy `sudo dmesg | tail -500`:

![dfsptree](/Week2/Images/Screenshot%20from%202024-02-27%2000-27-51.png)

Kết quả chạy lệnh bỏ module:

![dfsptree](/Week2/Images/Screenshot%20from%202024-02-27%2000-30-33.png)

## Bài 3.14

<u>Đáp án:</u>

A. pid = 0<br>
B. pid1 = 2603<br>
C. pid = 2603<br>
D. pid1 = 2600<br>

<u>Giải thích:</u>

Sau khi chạy hàm fork(), một tiến trình con được sinh ra và là một bản sao của tiến trình cha. Cả 2 tiến trình tiếp tục thực thi từ lệnh đầu tiên sau lệnh fork(). Ở tiến trình con, hàm fork() trả về 0, trong khi ở tiến trình cha, hàm fork() trả về một giá trị > 0 là pid của tiến trình con. Như vậy ta giải thích được A. pid = 0 và C. pid = 2603. Hàm getpid() trả về pid của tiến trình gọi hàm đó, do đó B. pid1 = 2603 và D. pid1 = 2600.

## Bài 3.12

<u>Đáp án</u>: 16

Đây là cây tiến trình nhận được sau một lần chạy chương trình [4fork.c](/Week2/Fork/4fork.c):

![process tree](/Week2/Images/3.12%20process%20tree.png)

Ở đây, `4798` là tiến trình `bash` và `22662` là tiến trình 4fork tổ tiên được chạy từ bash. `22662` chạy 4 lệnh fork() nên nó sinh ra 4 tiến trình con `22663`, `22664`, `22665` và `22666`. `22663` là tiến trình được sinh ra từ lệnh fork đầu tiên của `22662` nên bản thân nó sẽ chạy thêm 3 lệnh fork() nữa, do đó nó sinh ra được 3 tiến trình con. Tương tự, `22664` sinh ra 2 tiến trình con, `22665` sinh ra 1 và `22666` sinh ra 0.

Như vậy, một tiến trình chạy $n$ lệnh fork thì sinh ra $n$ tiến trình con, và các tiến trình con đó sinh ra lần lượt $n-1, n-2, ..., 1, 0$ tiến trình con.
