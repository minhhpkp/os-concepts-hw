#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/sched.h>
#include <linux/sched/signal.h>

void dfs(struct task_struct *task) {
  struct list_head *list;
  printk(KERN_INFO "%-20s\t%c\t%d\n", task->comm, task_state_to_char(task),
         task->pid);
  list_for_each(list, &task->children) {
    struct task_struct *child = list_entry(list, struct task_struct, sibling);
    dfs(child);
  }
}

/* This function is called when the module is loaded. */
int tasklist_init(void) {
  printk(KERN_INFO "Loading Module dfsptree\n");
  printk(KERN_INFO "%-20s\tstate\tpid\n", "name");
  dfs(&init_task);

  return 0;
}

/* This function is called when the module is removed. */
void tasklist_exit(void) { printk(KERN_INFO "Removing Module dfsptree\n"); }

/* Macros for registering module entry and exit points. */
module_init(tasklist_init);
module_exit(tasklist_exit);

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION(
    "A kernel module that lists all current tasks in a Linux system by "
    "iterating over the tasks with a Depth-First Search Tree");
MODULE_AUTHOR("Minh");
