package com.umpay.proxyservice.processStep;

/**
 * 链式结构的流程步骤
 * 
 * @author xuxiaojia
 */
public abstract class AbsProcessStep<T> implements ProcessStep<T> {
	/* 当前流程步骤信息 */
	private StepInfo stepInfo;

	/* 下一步 */
	private AbsProcessStep<T> nextProcessStep;

	public AbsProcessStep(StepInfo stepInfo) {
		super();
		this.stepInfo = stepInfo;
	}
	
	public void addProcessStep(AbsProcessStep<T> processStep){
		if(nextProcessStep==null){
			nextProcessStep=processStep;
		}else{
			nextProcessStep.addProcessStep(processStep);
		}
	}

	public StepInfo getStepInfo() {
		return stepInfo;
	}

	public void setStepInfo(StepInfo stepInfo) {
		this.stepInfo = stepInfo;
	}

	@Override
	public T process(T t) {
		if (isCurrStepProcess(t)) {
			t = doProcess(t);
			if(nextProcessStep!=null){
				t = putNextStep2task(nextProcessStep.stepInfo,t);
			}
		}
		return t;
	}

	/**
	 * 将下一步的流程信息存当前流程对象
	 * @param stepInfo
	 * @param t
	 */
	protected abstract T putNextStep2task(StepInfo stepInfo,T t);

	@Override
	public T linkedProcess(T t) {
		if(nextProcessStep!=null){
			return nextProcessStep.linkedProcess(process(t));
		}else{
			return process(t);
		}
	}
	/**
	 * 执行
	 * 
	 * @param t
	 * @return
	 */
	protected abstract T doProcess(T t);

	/**
	 * 判断此任务是否是当前流程
	 * 
	 * @param t
	 * @return
	 */
	protected abstract boolean isCurrStepProcess(T t);

}
