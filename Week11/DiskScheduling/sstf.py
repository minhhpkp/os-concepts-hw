from diskscheduling import DiskScheduling


class ShortestSeekTimeFirst(DiskScheduling):
    def run(
        self,
        start: int,
        requests: list[int],
        _before_start: int,
        _max_cylinder_index: int,
    ) -> tuple[int, list[int]]:
        # remove duplicate requests
        requests = list(set(requests))
        # service all requests for the start cylinder
        if start in requests:
            requests.remove(start)
        # the current cylinder where the head is at
        current = start
        order = [start]
        total_movement = 0
        while len(requests) > 0:
            # find the request that has the shortest seek time from the current cylinder
            min_seek_time = abs(current - requests[0])
            next_request = 0
            for i in range(len(requests)):
                seek_time = abs(current - requests[i])
                if seek_time < min_seek_time:
                    min_seek_time = seek_time
                    next_request = i
            total_movement += min_seek_time
            current = requests.pop(next_request)
            order.append(current)
        return (total_movement, order)

    def __str__(self) -> str:
        return "SSTF"


if __name__ == "__main__":
    sstf = ShortestSeekTimeFirst()
    sstf.demo()
