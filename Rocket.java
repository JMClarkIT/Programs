package asteroidgame;

import blobzx.BlobUtils;
import blobzx.PolyBlob;
import blobzx.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;
import java.lang.Math;


public class Rocket extends PolyBlob implements BlobAction, BlobProximity {
    
    private final int[] x = \{4,0 , 9, 0\}; 
    private final int[] y = \{0, 7, 0, -7\};
    private final int[] xp = \{4,0 , 9, 0\};
    private final int[] yp = \{0, 7, 0, -7\};
    private double angle = 0.0;
    private final double delta = 0.15;
    private final double speed = 5.0;
    private SandBox sb;
    
    public Rocket(int xx, int yy , SandBox sb) {
        super(0, 0, 0);
        this.sb = sb;
        setLoc(xx, yy);
        setPolygon(x, y);
    }
    
    /* 
       The turn method is used when either the left or right arrow
       keys are pressed to turn the rocket.
    */
    public void turn()
    {
       for (int i = 0; i < 4; i++)
       {
           Point p = BlobUtils.rotatePoint(xp[i], yp[i], angle);
           x[i] = p.x;
           y[i] = p.y;
       }
    }
    
    /*
       This method is used to launch the missle from the tip of the
       rocket.
    */
    public void launch(SandBox sb)
    {
        int radius = (getSize()/2) + 5; // Missle launched 5 pixels ahead of rocket.
        Point p = BlobUtils.rotatePoint(radius, angle);
        int xl = p.x + getLoc().x;
        int yl = p.y + getLoc().y;
        
        Missle g = new Missle(xl, yl, angle);
        sb.addBlob(g);
    }
    
    /*
       The key action method is used when the arrow keys are pressed to
       turn the rocket. The x and y locations of the rocket is updated
       to new coordinates after the rocket is turned. The turn method is
       called at the end up each key press.
    */
    public void keyAction(KeyEvent e)
    {
       if (e.getKeyCode() == 38)
        {
           int xloc = this.getLoc().x;
           int yloc = this.getLoc().y;
           xloc = xloc + (int)Math.round(speed*Math.cos(angle));
           yloc = yloc + (int)Math.round(speed*Math.sin(angle));
           setLoc(xloc, yloc);
           
        }
       else if (e.getKeyCode() == 37)
       {
          angle -= delta;
          if (angle < 0)
          {
              angle += Math.PI*2;
          }
          turn();
       }
       else if (e.getKeyCode() == 39)
       {
           angle += delta;
           if (angle>0)
           {
               angle -= Math.PI*2;
           }
           turn();
       }
       else if (e.getKeyCode() == 32)
       {
          launch(sb);
          BlobUtils.playSound();
       }
    }
          
}
}