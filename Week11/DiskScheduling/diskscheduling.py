from abc import ABC, abstractmethod
import numpy as np
import matplotlib.pyplot as plt


class DiskScheduling(ABC):
    @abstractmethod
    def run(
        self,
        start: int,
        requests: list[int],
        before_start: int,
        max_cylinder_index: int,
    ) -> tuple[int, list[int]]:
        """
        Runs the disk scheduling algorithm and calculates the total head movement and the order of the cylinders visited by the disk head.

        Args:
            start (int): the cylinder at which the disk head is initially.
            before_start (int): the cylinder where the second to last request before the start cylinder is completed.
            requests (list[int]): the pending requests for the disk drive.

        Returns:
            tuple[int, list[int]]: A tuple containing the total head movement and the order of the visited cylinders.
        """
        pass

    def demo(self):
        with open(".\\Week11\\DiskScheduling\\input.txt", "r") as file:
            start, before_start = [
                int(num.strip()) for num in file.readline().split(" ")
            ]
            requests = [int(num.strip()) for num in file.readline().split(",")]
            max_cylinder_index = int(file.readline().strip())
            total_movement, order = self.run(
                start, requests, before_start, max_cylinder_index
            )

            print(total_movement, order)

            # Define the points
            points = [np.array([order[i], i + 1]) for i in range(len(order))]

            # Create a figure and axis
            _fig, ax = plt.subplots(figsize=(16, 5))

            # Plot the points
            for point in points:
                ax.scatter(*point, color="blue", label="Point")

            for i in range(1, len(points)):
                # Calculate the vector (arrow) from point1 to point2
                vector = points[i] - points[i - 1]

                # Plot the arrow
                ax.quiver(
                    *points[i - 1],
                    *vector,
                    angles="xy",
                    scale_units="xy",
                    scale=1,
                    color="blue",
                    label="Vector",
                    width=0.002,
                )

            # Invert the Y-axis and place the X-axis at the top
            ax.invert_yaxis()
            ax.xaxis.tick_top()  # Place X-axis at the top

            # Set axis limits
            # ax.set_xlim(min(order) - 1, max(order) + 1)
            ax.set_xlim(0, max_cylinder_index)
            ax.set_ylim(len(order) + 1, 0)  # Inverted Y-axis

            # Add axis lables and title
            ax.set_xlabel("Cylinder")
            ax.set_ylabel("Time")
            ax.set_title(f"{self.__str__()} Disk Scheduling")

            # Set custom X-axis ticks
            ax.set_xticks([0, *[point[0] for point in points], max_cylinder_index])

            # # Set custom Y-axis ticks
            ax.set_yticks([point[1] for point in points])
            plt.text(
                (max_cylinder_index + 1) / 2,
                len(order) + 2,
                f"Total head movement: {total_movement} cylinders",
                fontsize=12,
                ha="center",
            )

            # Show the plot
            plt.grid()
            plt.tight_layout()
            plt.show()
