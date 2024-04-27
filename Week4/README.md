# Bài tập về nhà tuần 4

**Đáp án:** P0 sẽ in ra 2 hoặc 3 lần '0'.

**Giải thích:**

Thứ nhất: Ta chứng minh được ở một vòng lặp while của P0, khi chạy qua `wait(S0)`, sau một khoảng thời gian hữu hạn, P0 sẽ chạy qua các lệnh còn lại của vòng lặp này và chuyển tiếp đến vòng lặp sau. Điều này là vì P1 và P2 chỉ chạy trong hữu hạn thời gian.

Thứ hai: Ta chứng minh được P0 sẽ in ra tối thiểu 2 lần '0'.  
Vì S0 được khởi tạo bằng 0 nên `wait(S0)` ở vòng lặp while thứ nhất của S0 chắc chắc được chạy qua, do đó, sau một khoảng thời gian hữu hạn, P0 sẽ chạy qua `print '0'`, in ra 1 lần '0', chạy qua các lệnh còn lại trong vòng lặp rồi chuyển sang vòng lặp thứ hai.
Vì S2 được khởi tạo bằng 0 và `signal(S2)` chỉ ở một chỗ duy nhất là cuối vòng lặp while của P0 nên vòng lặp đầu tiên của P0 luôn được chạy trước P2. Sau vòng lặp đầu tiên, chắc chắn P2 sẽ được chạy và sẽ bật S0 ở lệnh `signal(S0)` của P2, do đó, chắc chắn lệnh `wait(S0)` ở vòng lặp 2 của S0 sẽ được chạy qua. Do đó, sau một khoảng thời gian hữu hạn, lệnh `print '0'` lại được chạy và in ra lần '0' thứ 2.

Thứ ba: Ta chứng minh được P0 không in được hơn 3 lần '0'.  
Vì S1 được khởi tạo bằng 0 và lệnh `signal(S0)` chỉ có thể được chạy đúng 2 lần, 1 lần ở P1 và 1 lần ở P2, nên S0 sẽ chỉ được bật lên được đúng 3 lần. Do đó lệnh `wait(S0)` sẽ chỉ được chạy qua tối đa 3 lần, và `print '0'` cũng chỉ được chạy qua tối đa 3 lần, nên P0 chỉ in tối đa 3 lần '0'.

Thứ 4: Ta chỉ ra được một trường hợp mà P0 có thể in 3 lần '0'.  
P0 sẽ in ra 3 lần '0' trong trường hợp sau đây:

1. P0 chạy qua `wait(S0)` ở vòng lặp while đầu tiên vì S0 khởi tạo bằng 1, do đó in ra 1 lần '0', bật S2 và chuyển sang `wait(S0)` ở vòng lặp 2.
2. P1 chạy, S1 đã được bật từ lúc khởi tạo nên chạy qua được `wait(S1)`, bật S0 và kết thúc P1.
3. P0 bắt đầu chạy qua được `wait(S0)` ở vòng lặp 2 do P1 vừa bật được S0, và in ra lần '0' thứ 2, chạy hết vòng lặp 2 và chuyển sang `wait(S0)` ở vòng lặp 3.
4. P2 bắt đầu chạy, chạy qua được `wait(S2)` vì S2 được bật ở bước 1, bật S0 và kết thúc P2.
5. P0 chạy được vòng lặp 3, in thêm lần '0' thứ 3, và kết thúc vòng lặp 3. Chuyển sang vòng lặp 4, P0 không thể chạy qua `wait(S0)` được nữa vì không còn `signal(S0)` nào có thể được chạy thêm 1 lần nữa, nên P0 không in thêm được lần '0' nào nữa.
