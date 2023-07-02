public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public Body(double xP, double yP, double xV, double yV, double m, String img)
    {
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }
    public Body(Body b)
    {
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
        mass=b.mass;
        imgFileName=b.imgFileName;
    }

    public double calcDistance(Body b)
    {
        return Math.sqrt(Math.pow(this.xxPos-b.xxPos,2)+Math.pow(this.yyPos-b.yyPos,2));
    }

    public double calcForceExertedBy(Body b)
    {
        double G=6.67e-11;
        return (G*this.mass*b.mass)/Math.pow(this.calcDistance(b),2);
    }

    public double calcForceExertedByX(Body b)
    {
        double dx = this.xxPos - b.xxPos;
        if (dx < 0)
        {
            dx=b.xxPos - this.xxPos;
        }
        return this.calcForceExertedBy(b) * dx / this.calcDistance(b);
    }

    public double calcForceExertedByY(Body b)
    {
        double dy = this.yyPos - b.yyPos;
        if (dy < 0)
        {
            dy=b.yyPos - this.yyPos;
        }
        return this.calcForceExertedBy(b) * dy / this.calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] allBodys)
    {
        double F_net_X=0;
        for(int i=0;i<allBodys.length;i++)
        {
            if(allBodys[i]==this)
            {
                continue;
            }
            else
            {
                F_net_X+=this.calcForceExertedByX(allBodys[i]);
            }
        }
        return F_net_X;
    }

    public double calcNetForceExertedByY(Body[] allBodys)
    {
        double F_net_Y=0;
        for(int i=0;i<allBodys.length;i++)
        {
            if(allBodys[i]==this)
            {
                continue;
            }
            else
            {
                F_net_Y+=this.calcForceExertedByY(allBodys[i]);
            }
        }
        return F_net_Y;
    }

    public void update(double dt,double fx,double fy)
    {
        double ax=fx/this.mass;
        double ay=fy/this.mass;
        this.xxVel+=ax*dt;
        this.yyVel+=ay*dt;
        this.xxPos+=this.xxVel*dt;
        this.yyPos+=this.yyVel*dt;
    }

    public void draw()
    {
        StdDraw.picture(this.xxPos,this.yyPos,"images/"+this.imgFileName);
    }
}
