from diskscheduling import DiskScheduling


class FirstComeFirstServed(DiskScheduling):
    def run(
        self,
        start: int,
        requests: list[int],
        _before_start: int,
        _max_cylinder_index: int,
    ) -> tuple[int, list[int]]:
        if len(requests) == 0:
            return 0, [start]

        total_movement = requests[0] - start
        for i in range(1, len(requests)):
            total_movement += abs(requests[i] - requests[i - 1])
        return total_movement, [start, *requests]

    def __str__(self) -> str:
        return "FCFS"


if __name__ == "__main__":
    fcfs = FirstComeFirstServed()
    fcfs.demo()
