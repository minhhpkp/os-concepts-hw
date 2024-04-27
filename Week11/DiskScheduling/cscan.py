from diskscheduling import DiskScheduling


class CircularScan(DiskScheduling):
    def run(
        self,
        start: int,
        requests: list[int],
        before_start: int,
        max_cylinder_index: int,
    ) -> tuple[int, list[int]]:
        # remove duplicate requests
        requests.append(start)
        requests = list(set(requests))
        requests.sort()
        start_pos = requests.index(start)
        # if the head is moving towards 0
        if before_start > start:
            # first add the requests completed during the scan from start to request 0
            order = [requests[i] for i in reversed(range(0, start_pos + 1))]
            # if there are requests > start, we need to scan from the max cylinder
            if requests[len(requests) - 1] > start:
                order.extend(
                    [
                        0,
                        max_cylinder_index,
                        *[
                            requests[i]
                            for i in reversed(range(start_pos + 1, len(requests)))
                        ],
                    ]
                )
                total_movement = (
                    start
                    # + max_cylinder_index
                    + max_cylinder_index
                    - requests[start_pos + 1]
                )
            else:
                total_movement = start - requests[0]
        else:  # if the head is moving towards the max cylinder
            # first add the requests completed during the scan from start to max request
            order = [requests[i] for i in range(start_pos, len(requests))]
            # if there are requests < start, we need to do one more scan from cylinder 0
            if requests[0] < start:
                order.extend(
                    [max_cylinder_index, 0, *[requests[i] for i in range(0, start_pos)]]
                )
                total_movement = (
                    max_cylinder_index
                    - start
                    # + max_cylinder_index
                    + requests[start_pos - 1]
                )
            else:
                total_movement = requests[len(requests) - 1] - start
        return (total_movement, order)

    def __str__(self) -> str:
        return "C-SCAN"


if __name__ == "__main__":
    cscan = CircularScan()
    cscan.demo()
