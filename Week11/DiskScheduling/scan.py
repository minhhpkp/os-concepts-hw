from diskscheduling import DiskScheduling


class Scan(DiskScheduling):
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
        # if head is intially moving towards 0
        if before_start > start:
            # first service the requests on the scan from start to request 0
            order = [requests[i] for i in reversed(range(0, start_pos + 1))]
            # if there are requests for cylinders > start, do the reverse scan and service the remaining requests
            if requests[len(requests) - 1] > start:
                total_movement = start + requests[len(requests) - 1]
                order.extend(
                    [
                        0,
                        *[requests[i] for i in range(start_pos + 1, len(requests))],
                    ]
                )
            else:  # otherwise stop at request 0
                total_movement = start - requests[0]
        else:  # head is moving towards max cylinder
            # first service the requests on the scan from start to max request
            order = [requests[i] for i in range(start_pos, len(requests))]
            # if there are requests for cylinders < start, reverse the arm the service the remaining requests
            if requests[0] < start:
                total_movement = (
                    max_cylinder_index - start + max_cylinder_index - requests[0]
                )
                order.extend(
                    [
                        max_cylinder_index,
                        # requests completed after the head has redirected
                        *[requests[i] for i in reversed(range(0, start_pos))],
                    ]
                )
            else:  # otherwise stop at max request
                total_movement = requests[len(requests) - 1] - start
        return (total_movement, order)

    def __str__(self) -> str:
        return "SCAN"


if __name__ == "__main__":
    scan = Scan()
    scan.demo()
