abstract class Shape {

	protected RendererBridge renderer;

	public Shape (RendererBridge renderer) {
		this.renderer = renderer;
	}
  public abstract void draw();
}

class Circle extends Shape {

  public Circle(RendererBridge renderer) {
		super(renderer);
	}

	public float radiusX, radiusY;

  @Override
  public void draw() {
		renderer.renderCircle(radiusX, radiusY);
  }
}

class Rectangle extends Shape {

  public Rectangle(RendererBridge renderer) {
		super(renderer);
	}

	public float sideX, sideY;

  @Override
  public void draw() {
		renderer.renderRect(sideX, sideY);
  }
}

interface RendererBridge {

	void renderCircle(float radiusX, float radiusY);
	void renderRect(float sideX, float sideY);
}

class VectorRendererBridge implements	RendererBridge {

	@Override
	public void renderCircle(float radiusX, float radiusY) {
		System.out.println("Drawing a *vector* circle of radius X " + radiusX + " and radius Y " + radiusY);
	}

	@Override
	public void renderRect(float sideX, float sideY) {
		System.out.println("Drawing a *vector* rectangle of side X " + sideX + " and side Y " + sideY);
	}
}

class RasterRendererBridge implements RendererBridge {

	@Override
	public void renderCircle(float radiusX, float radiusY) {
		System.out.println("Drawing a *raster* circle of radius X " + radiusX + " and radius Y " + radiusY);
	}

	@Override
	public void renderRect(float sideX, float sideY) {
		System.out.println("Drawing a *raster* circle of side X " + sideX + " and side Y " + sideX);
	}
}

public class Bridge {
    public static void main(String[] args) {
        Circle circle = new Circle(new RasterRendererBridge());
				circle.radiusX = 5;
				circle.radiusY = 5;

				circle.draw();

				Rectangle rect = new Rectangle(new VectorRendererBridge());
				rect.sideX = 10;
				rect.sideY = 5;

				rect.draw();
    }
}

