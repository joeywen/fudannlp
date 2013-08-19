package edu.fudan.ontology.graph;



public enum WordRelationEnum {
	
	
	
	SYM("同义词",Direction.BOTH),
	ANTONYM("反义词",Direction.BOTH);
	
	private String cname;
	
	private Direction direction;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	private WordRelationEnum(String name,Direction direction){
		this.cname = name;
		this.direction = direction;
	}

	public static WordRelationEnum getWithName(String name) {
		WordRelationEnum[] tasks = WordRelationEnum.values();
		for(WordRelationEnum task:tasks){
			if(task.cname.equals(name))
				return task;
		}
		return null;
	}

}
