from diskscheduling import DiskScheduling


class Look(DiskScheduling):
    def run(
        self,
        start: int,
        requests: list[int],
        before_start: int,
        _max_cylinder_index: int,
    ) -> tuple[int, list[int]]:
        # remove duplicate requests
        requests.append(start)
        requests = list(set(requests))
        requests.sort()
        start_pos = requests.index(start)
        total_movement = 0
        order = [start]
        # if the head is moving towards 0
        if before_start > start:
            # if there are requests < start then complete those on the scan towards 0
            if requests[0] < start:
                total_movement = start - requests[0]
                order.extend([requests[i] for i in reversed(range(0, start_pos))])
            # if there are requests > start then reverse the arm and service those requests
            if requests[len(requests) - 1] > start:
                total_movement += requests[len(requests) - 1] - requests[0]
                order.extend([requests[i] for i in range(start_pos + 1, len(requests))])
        else:  # if the head is moving towards max cylinder
            # if there are requests > start then complete those on the scan towards max cylinder
            if requests[len(requests) - 1] > start:
                total_movement = requests[len(requests) - 1] - start
                order.extend([requests[i] for i in range(start_pos + 1, len(requests))])
            # if there are requests < start then service those on the reverse scan
            if requests[0] < start:
                total_movement += requests[len(requests) - 1] - requests[0]
                order.extend([requests[i] for i in reversed(range(0, start_pos))])
        return (total_movement, order)

    def __str__(self) -> str:
        return "LOOK"


if __name__ == "__main__":
    look = Look()
    look.demo()
