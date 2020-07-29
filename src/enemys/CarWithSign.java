package enemys;

public class CarWithSign extends ImpatientCar  {
	MissleadingOneWay way; 
	MissleadingStop stop;
	boolean stopOrWay = false;
	boolean inzialized = false;
	public CarWithSign () {
		super();
	}
	public void frameEvent () {
		if (!inzialized) {
			if (this.getVariantAttribute("type") == null) {
				if (this.getVariantAttribute("type").equals("oneWay")) {
					stopOrWay = true;
					way = new MissleadingOneWay();
					way.declare();
				} else {
					stopOrWay = false;
					stop = new MissleadingStop();
					stop.declare();
				}
			} else {
				stopOrWay = false;
				stop = new MissleadingStop();
				stop.declare();
			}
			inzialized = true;
		}
		if (stopOrWay) {
			way.setX(this.getX());
			way.setY(this.getY());
		}
		super.frameEvent();
	}
}
