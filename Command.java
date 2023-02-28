import java.util.Stack;

abstract class Shape {

	protected RendererBridge renderer;

	public Shape (RendererBridge renderer) {
		this.renderer = renderer;
	}
  public abstract void draw();
  public abstract void resize(float scaleX, float scaleY);
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

  @Override
  public void resize(float scaleX, float scaleY) {
    radiusX *= scaleX;
    radiusY *= scaleY;
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

  @Override
  public void resize(float scaleX, float scaleY) {
    sideX *= scaleX;
    sideY *= scaleY;
  }
}

interface RendererBridge {

	void renderCircle(float radiusX, float radiusY);
	void renderRect(float sideX, float sideY);
}

class VectorRendererBridge implements	RendererBridge {

	@Override
	public void renderCircle(float radiusX, float radiusY) {
		System.out.println("Drawing a *vertor* circle of radius X " + radiusX + " and radius Y " + radiusY);
	}

	@Override
	public void renderRect(float sideX, float sideY) {
		System.out.println("Drawing a *vertor* rectangle of side X " + sideX + " and side Y " + sideY);
	}
}

class RasterRendererBridge implements RendererBridge {

	@Override
	public void renderCircle(float radiusX, float radiusY) {
		System.out.println("Drawing a *raster* circle of radius X " + radiusX + " and radius Y " + radiusY);
	}

	@Override
	public void renderRect(float sideX, float sideY) {
		System.out.println("Drawing a *raster* circle of side X " + sideX + " and side Y " + sideY);
	}
}

interface Command1 {
  void call();
  void undo();
}

// In future
// class FillColourCommand implements Command1 {

//   @Override
//   public void call() {
//     // TODO Auto-generated method stub
//     throw new UnsupportedOperationException("Unimplemented method 'call'");
//   }

//   @Override
//   public void undo() {
//     // TODO Auto-generated method stub
//     throw new UnsupportedOperationException("Unimplemented method 'undo'");
//   }
  
// }

class ResizeShapeCommand implements Command1 {

  private Shape shape;
  private float scaleX, scaleY;

  public ResizeShapeCommand (Shape shape, float scaleX, float scaleY) {
    this.shape = shape;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
  }

  @Override
  public void call() {
    shape.resize(scaleX, scaleY);
  }

  @Override
  public void undo() {
    shape.resize(1/scaleX, 1/scaleY);
  }
}

public class Command {
    public static void main(String[] args) {
        Circle circle = new Circle(new RasterRendererBridge());
				circle.radiusX = 5;
				circle.radiusY = 5;

				circle.draw();

				Rectangle rect = new Rectangle(new VectorRendererBridge());
				rect.sideX = 10;
				rect.sideY = 5;
				rect.draw();

        rect.resize(2, 5);
        rect.draw();

        ResizeShapeCommand resizeShapeCommand = new ResizeShapeCommand(circle, 4, 4);
        resizeShapeCommand.call();  // call() is resize
        circle.draw();
        resizeShapeCommand.undo();
        circle.draw();

        Stack<Command1> commands = new Stack<>();
        commands.push(new ResizeShapeCommand(rect, 10, 5));
        commands.push(new ResizeShapeCommand(circle, 5, 5));
        commands.push(new ResizeShapeCommand(circle, 10, 1));
        commands.push(new ResizeShapeCommand(rect, 1, 20));

        for (Command1 c : commands) {
          System.out.println("==========");
          c.call();
          circle.draw();
          rect.draw();
        }

        System.out.println("== UNDO ==");
        while (!commands.isEmpty()) {
          System.out.println("==========");
          Command1 c = commands.pop();
          c.undo();
          circle.draw();
          rect.draw();
        }
    }
}

