package invmod.client;

public class BowHackHandler
{
  private int bowDrawTime;
  private boolean bowDrawing;

  public void onUpdate()
  {
    if (this.bowDrawing)
    {
      this.bowDrawTime += 50;
    }
  }

  public void setBowDrawing(boolean flag)
  {
    this.bowDrawing = flag;
  }

  public void setBowReleased()
  {
    this.bowDrawing = false;
    this.bowDrawTime = 0;
  }

  public int getDrawTimeLeft()
  {
    return this.bowDrawTime;
  }

  public boolean isBowDrawing()
  {
    return this.bowDrawing;
  }
}