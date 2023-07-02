public class NBody {
        public static double readRadius(String FileName)
        {
            In in=new In(FileName);
            in.readInt();
            return in.readDouble();
        }

        public static Body[] readBodies(String FileName)
        {
            In in=new In(FileName);
            int Num=in.readInt();
            in.readDouble();
            Body[] Planets=new Body[Num];
            for(int i=0;i<Num;i++)
            {
                double xp=in.readDouble();
                double yp=in.readDouble();
                double xv=in.readDouble();
                double yv=in.readDouble();
                double m=in.readDouble();
                String img=in.readString();
                Body b=new Body(xp,yp,xv,yv,m,img);
                Planets[i]=b;
            }
            return Planets;
        }

    public static void main(String args[])
    {
        double T=Double.parseDouble(args[0]);
        double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        double r=NBody.readRadius(filename);
        Body[] Planets=NBody.readBodies(filename);
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for(int j=0;j<Planets.length;j++)
        {
            Planets[j].draw();
        }
        StdDraw.enableDoubleBuffering();
        for(int t=0;t<=T;t+=dt)
        {
            double[] xForce=new double[Planets.length];
            double[] yForce=new double[Planets.length];
            for(int i=0;i<Planets.length;i++)
            {
                xForce[i]=Planets[i].calcNetForceExertedByX(Planets);
                yForce[i]=Planets[i].calcNetForceExertedByY(Planets);
            }
            for(int i=0;i<Planets.length;i++)
            {
                Planets[i].update(dt,xForce[i],yForce[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i=0;i<Planets.length;i++)
            {
                Planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < Planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                    Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);
        }
    }
}