package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.domain.Task;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

/*
*类名和方法不能修改
 */
public class Schedule {

    private Set<Integer> nodes = new TreeSet<Integer>();
    private Map<Integer, Task> tasks1 = new HashMap<Integer, Task>();
    private Map<Integer, Task> tasks2 = new HashMap<Integer, Task>();

    private boolean invalidParam(int id) {
        return id <= 0;
    }

    private boolean exist(int id) {
        return tasks1.containsKey(id) || tasks2.containsKey(id);
    }

    private boolean existNode(int nodeId) {
        return nodes.contains(nodeId);
    }

    public int init() {
        nodes.clear();
        tasks1.clear();
        tasks2.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        if (invalidParam(nodeId)) {
            return ReturnCodeKeys.E004;
        }
        if (existNode(nodeId)) {
            return ReturnCodeKeys.E005;
        }

        nodes.add(nodeId);
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if (invalidParam(nodeId)) {
            return ReturnCodeKeys.E004;
        }
        if (!existNode(nodeId)) {
            return ReturnCodeKeys.E007;
        }

        nodes.remove(nodeId);
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        if (invalidParam(taskId)) {
            return ReturnCodeKeys.E009;
        }
        if (exist(taskId)) {
            return ReturnCodeKeys.E010;
        }

        tasks1.put(taskId, new Task(taskId, consumption, -1));
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        if (invalidParam(taskId)) {
            return ReturnCodeKeys.E009;
        }
        if (!exist(taskId)) {
            return ReturnCodeKeys.E012;
        }

        if (tasks1.containsKey(taskId)) {
            tasks1.remove(taskId);
        }
        else tasks2.remove(taskId);

        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        if (invalidParam(threshold)) {
            return ReturnCodeKeys.E002;
        }

        return ReturnCodeKeys.E013;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        if (tasks == null) return ReturnCodeKeys.E016;

        tasks.clear();
        for (Task task : tasks1.values()) {
            tasks.add(covertTask2TaskInfo(task));
        }

        for (Task task : tasks2.values()) {
            tasks.add(covertTask2TaskInfo(task));
        }

        Collections.sort(tasks, new Comparator<TaskInfo>() {
            public int compare(TaskInfo o1, TaskInfo o2) {
                return o1.getTaskId() - o2.getTaskId();
            }
        });
        return ReturnCodeKeys.E015;
    }

    private TaskInfo covertTask2TaskInfo(Task task) {
        TaskInfo info = new TaskInfo();
        info.setTaskId(task.getTaskId());
        info.setNodeId(task.getNodeId());
        return info;
    }

}
