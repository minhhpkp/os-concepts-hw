from diskscheduling import DiskScheduling


class CircularLook(DiskScheduling):
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
        # if the head is intially moving towards cylinder 0
        if before_start > start:
            # service requests for cylinders < start if those exist
            if requests[0] < start:
                total_movement = start - requests[0]
                order.extend([requests[i] for i in reversed(range(0, start_pos))])
            # service requests for cylinders > start if those exist
            if requests[len(requests) - 1] > start:
                # # if the direction from start to cylinder 0 does contain some requested cylinders
                # # which means the arm needs to move from cylinder of request 0 to that of max request
                # if requests[0] < start:
                #     total_movement += requests[len(requests) - 1] - requests[0]
                # # otherwise the arm will move from start to cylinder of max request
                # else:
                #     total_movement += requests[len(requests) - 1] - start
                total_movement += requests[len(requests) - 1] - requests[start_pos + 1]
                order.extend(
                    [requests[i] for i in reversed(range(start_pos + 1, len(requests)))]
                )
        else:  # if the head is initially moving towards max cylinder
            # first service requests for cylinders > start if those exist
            if requests[len(requests) - 1] > start:
                total_movement = requests[len(requests) - 1] - start
                order.extend([requests[i] for i in range(start_pos + 1, len(requests))])
            # then service requests for cylinder < start if those exists
            if requests[0] < start:
                # # if the direction from start to max cylinder does contain some requested cylinders
                # # which means the arm needs to move from cylinder of max request to that of request 0
                # if requests[len(requests) - 1] > start:
                #     total_movement += requests[len(requests) - 1] - requests[0]
                # # otherwise the arm will move from start to request 0
                # else:
                #     total_movement += start - requests[0]
                total_movement += requests[start_pos - 1] - requests[0]
                order.extend([requests[i] for i in range(0, start_pos)])
        return (total_movement, order)

    def __str__(self) -> str:
        return "C-LOOK"


if __name__ == "__main__":
    clook = CircularLook()
    clook.demo()
