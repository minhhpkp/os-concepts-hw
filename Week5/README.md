# Bài về nhà tuần 5

## Thuật toán banker và thuật toán nhận diện bế tắc

Trong file [Banker.java](./Banker.java) có phần cài đặt của:

1. thuật toán kiểm tra xem hệ thống có đang ở trạng thái an toàn hay không (phương thức `checkSafety`),
2. thuật toán xác định xem có thể cấp tài nguyên cho một yêu cầu của một tiến trình hay không (phương thức `canSafelyGrantResources`) và
3. thuật toán phát hiện bế tắc (phương thức `detectDeadlocks`).

## Bài tập Deadlock

Sử dụng phương thức `detectDeadlocks`, em nhận được output sau:

```text
The following sequence satisfies the deadlock-free criteria:
0 1 2 3
The system is deadlock-free.
```

Do đó hệ thống đã cho đang không ở trạng thái bế tắc.
