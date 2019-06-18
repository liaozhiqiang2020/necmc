package com.sv.mc.conf;

import com.sv.mc.quartz.ScheduleTask;
import com.sv.mc.quartz.ScheduleTask2;
import com.sv.mc.quartz.ScheduleTask3;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;


@Configuration
public class QuartzConigration {
    @Bean(name = "jobDetail1")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(ScheduleTask task) {
        // ScheduleTask为需要执行的任务
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        /*
         *  是否并发执行
         *  例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了，
         *  如果此处为true，则下一个任务会bing执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行
         */
        jobDetail.setConcurrent(true);

        jobDetail.setName("scheduler");// 设置任务的名字
        jobDetail.setGroup("scheduler_group");// 设置任务的分组，这些属性都可以存储在数据库中，在多任务的时候使用

        /*
         * 这两行代码表示执行task对象中的scheduleTest方法。定时执行的逻辑都在scheduleTest。
         */
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("scheduleTest");

        return jobDetail;
    }


    @Bean(name = "jobDetail2")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean2(ScheduleTask2 task) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("scheduleTest");

        return jobDetail;
    }

    @Bean(name = "jobDetail3")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean3(ScheduleTask3 task) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(true);
        jobDetail.setTargetObject(task);
        jobDetail.setTargetMethod("scheduleTest");

        return jobDetail;
    }

    @Bean(name = "jobTrigger1")
    public CronTriggerFactoryBean cronJobTrigger1(JobDetail jobDetail1) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail1);
        trigger.setCronExpression("0/60 * * * * ?");// 表示每隔6秒钟执行一次
//        trigger.setName("myTigger");// trigger的name
        return trigger;

    }

    @Bean(name = "simpleJobTrigger1")
    public SimpleTriggerFactoryBean simpleJobTrigger1(JobDetail jobDetail1) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(jobDetail1);
        trigger.setRepeatCount(0);
        trigger.setRepeatInterval(0);
        trigger.setStartDelay(5);
//        trigger.setName("myTigger");// trigger的name
        return trigger;

    }

    @Bean(name = "jobTrigger2")
    public CronTriggerFactoryBean cronJobTrigger2(JobDetail jobDetail2) {
        CronTriggerFactoryBean trigger2 = new CronTriggerFactoryBean();
        trigger2.setJobDetail(jobDetail2);
        trigger2.setCronExpression("0/60 * * * * ?");// 表示每隔6秒钟执行一次
//        trigger2.setName("myTigger");// trigger的name
        return trigger2;

    }

    @Bean(name = "jobTrigger3")
    public CronTriggerFactoryBean cronJobTrigger3(JobDetail jobDetail3) {
        CronTriggerFactoryBean trigger3 = new CronTriggerFactoryBean();
        trigger3.setJobDetail(jobDetail3);
        trigger3.setCronExpression("0/5 * * * * ?");// 表示每隔6秒钟执行一次
//        trigger2.setName("myTigger");// trigger的name
        return trigger3;

    }




    //多任务时的Scheduler，动态设置Trigger。一个SchedulerFactoryBean可能会有多个Trigger
    @Bean(name = "multitaskScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger simpleJobTrigger1,Trigger jobTrigger1,Trigger jobTrigger3) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        //设置是否任意一个已定义的Job会覆盖现在的Job。默认为false，即已定义的Job不会覆盖现有的Job。
        bean.setOverwriteExistingJobs(true);
        //项目启动6秒后执行
//        bean.setStartupDelay(6);
        // 注册定时触发器
//        bean.setTriggers(simpleJobTrigger1);
        return bean;
    }

}
