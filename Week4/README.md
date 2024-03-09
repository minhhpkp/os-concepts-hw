**Đáp án**: P0 được in ra đúng 3 lần '0'.

Chứng minh mệnh đề này bằng cách chứng minh:<br>
(1) _signal(S0) ở P1 được chạy đúng một lần._<br>
(2) _signal(S0) ở P2 được chạy đúng một lần._

Nếu (1) và (2) đúng thì signal(S0) được chạy 2 lần, và vì khởi tạo S0 = 1 nên wait(S0) (1 lần chạy do S0 khởi tạo = 1 và 2 lần chạy do 2 lần tăng S0 từ lệnh signal(S0) ở P1 và P2) được hoàn thành đúng 3 lần và do đó, chạy print '0' đúng 3 lần.

**Chứng minh (1)**: Vì khởi tạo S1 bằng 1 nên ta chắc chắn P1 sẽ chạy qua được wait(S1). Hơn nữa, vì ở các process còn lại là P0 và P2, chỉ có đúng 1 lệnh signal(S0), và P2 chỉ chạy đúng 1 lần (không có vòng lặp vĩnh viễn), nên vòng lặp ở P0 chỉ chạy nhiều nhất 2 lần (1 lần từ khởi tạo S0 = 1 và 1 lần do signal(S0) ở P2) và phải dừng sau đó. Do đó, sau một thời gian hữu hạn, signal(S0) ở P1 sẽ được chạy 1 lần rồi P1 dừng. Q.E.D.

**Chứng minh (2)**: Vì khởi tạo S2 = 0 nên wait(S2) ở P2 sẽ không được hoàn thành cho đến khi signal(S2) được chạy. Vì khởi tạo S0 = 1 và P1 chạy trong hữu hạn thời gian nên wait(S0) chắc chắn được chạy ít nhất một lần. Sau khi wait(S0) chạy lần đầu, cũng vì P1 chạy trong hữu hạn thời gian và print'0' và signal(S1) cũng chạy trong hữu hạn thời gian nên signal(S2) cũng được chạy ít nhất một lần. Và vì ngoài P2 chỉ có đúng 1 lệnh signal(S0) ở P1 nên sẽ đến một lúc mà P0 phải chờ ở wait(S0), và vì P1 không chạy vô hạn, nên sau một khoảng thời gian hữu hạn, wait(P2) ở P2 chắc chắn cũng được chạy qua. Sau đó, cũng sau một khoảng thời gian hữu hạn, signal(S0) ở P2 sẽ được chạy đúng 1 lần rồi P2 dừng. Q.E.D.
