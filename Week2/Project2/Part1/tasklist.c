#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/sched.h>
#include <linux/sched/signal.h>

/* This function is called when the module is loaded. */
int tasklist_init(void) {
  printk(KERN_INFO "Loading Module tasklist\n");
  printk(KERN_INFO "%-20s\tstate\tpid\n", "name");

  struct task_struct *task;
  for_each_process(task) {
    /* on each iteration task points to the next task */
    printk(KERN_INFO "%-20s\t%c\t%d\n", task->comm, task_state_to_char(task),
           task->pid);
  }

  return 0;
}

/* This function is called when the module is removed. */
void tasklist_exit(void) { printk(KERN_INFO "Removing Module tasklist\n"); }

/* Macros for registering module entry and exit points. */
module_init(tasklist_init);
module_exit(tasklist_exit);

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION(
    "A kernel module that lists all current tasks in a Linux system.");
MODULE_AUTHOR("Minh");
