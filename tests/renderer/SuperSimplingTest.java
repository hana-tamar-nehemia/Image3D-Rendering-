package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class SuperSimplingTest
{

    private Camera camera = new Camera(new Point3D(0,0,1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(1000) //
            .setViewPlaneSize(500, 500)
            .setNumOfRays(81);

    @Test
    public void TestWithSuperSampling() {
        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.10));

        scene.geometries.add(
                new Triangle(new Point3D(-100, -100, 0), new Point3D(-100, 100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(new Point3D(100, 100, 0), new Point3D(100, -100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.RED)), // right

                new Triangle(new Point3D(-100, 100, 0), new Point3D(100, 100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(new Point3D(-100, -100, 0), new Point3D(100, -100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.GREEN)), //down

                new Sphere(60d, new Point3D(-100, -100, 0))
                        .set_emission(new Color(java.awt.Color.ORANGE)) // left down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(100, 100, 0))
                        .set_emission(new Color(java.awt.Color.RED)) // right up
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(-100, 100, 0))
                        .set_emission(new Color(java.awt.Color.BLUE)) // left up
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(100, -100, 0))
                        .set_emission(new Color(java.awt.Color.GREEN)) // right down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)));


        scene._lights.add( //
                new SpotLight(new Color(700,600,600), new Point3D(0, 0, 300), new Vector(-1, -1, -4))//
                        .setKl(4E-3).setKq(2E-7)
        );

        ImageWriter imageWriter = new ImageWriter("With SuperSampling", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        //here we do the multi rays
        render.renderImageForSuperSampling();
        render.writeToImage();
    }

    @Test
    public void TestWithoutSuperSampling() {
        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.10));

        scene.geometries.add(
                new Triangle(new Point3D(-100, -100, 0), new Point3D(-100, 100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(new Point3D(100, 100, 0), new Point3D(100, -100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.RED)), // right

                new Triangle(new Point3D(-100, 100, 0), new Point3D(100, 100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(new Point3D(-100, -100, 0), new Point3D(100, -100, 0), new Point3D(0 ,0, 0))
                        .set_emission(new Color(java.awt.Color.GREEN)), //down

                new Sphere(60d, new Point3D(-100, -100, 0))
                        .set_emission(new Color(java.awt.Color.ORANGE)) // left down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(100, 100, 0))
                        .set_emission(new Color(java.awt.Color.RED)) // right up
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(-100, 100, 0))
                        .set_emission(new Color(java.awt.Color.BLUE)) // left up
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(60d, new Point3D(100, -100, 0))
                        .set_emission(new Color(java.awt.Color.GREEN)) // right down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)));


        scene._lights.add( //
                new SpotLight(new Color(700,600,600), new Point3D(0, 0, 300), new Vector(-1, -1, -4))//
                        .setKl(4E-3).setKq(2E-7)
        );

        ImageWriter imageWriter = new ImageWriter("Without SuperSampling", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        //here we do the one ray
        render.renderImage();
        render.writeToImage();
    }


    private Camera camera2 = new Camera(new Point3D(0,300,0), new Vector(0,-1,0), new Vector(0,0,1)) //
            .setViewPlaneSize(200, 200).setDistance(1000).setNumOfRays(81);
    ;

    @Test
    public void level2() {

             Point3D[] pnts = new Point3D[] {  //
            new Point3D(-6.99, 0.63, 16.08), //I 0
            new Point3D(-3.59, -2.69, 14.69), //J 1
            new Point3D(2.87 ,-3.26, 14.57), //K2
            new Point3D( 6.75,0.02 ,16.12), //L3
            new Point3D(7.05, 6.34, 18.93), ////m4
            new Point3D(3.73, 9.59,  20.29), //n5
            new Point3D(- 3.2, 9.95, 20.3), //o6
            new Point3D(- 6.87, 6.82, 18.83), //p7
            new Point3D(- 3.63, 3.44,17.4 ), //Q8
            new Point3D(0.13, 6.59, 18.88 ), //R9
            new Point3D(- 0.23, - 0.1,  15.91), //s10
            new Point3D( 3.42,3.26,  17.48 ), //T11
            new Point3D( -0.3, - 5.91, 13.34), //E12
            new Point3D(-10.31 , 3.88 ,17.45), //H13
            new Point3D(10.31 ,3.16, 17.59 ), //F14
            new Point3D(0.3 , 12.95,21.7 ), //G15

                new Point3D(0,0,0), //A16
                new Point3D(10.35, 9.08, 4.25), //B17
                new Point3D(7.41, 6.33, 2.97), //M 18
                new Point3D(3.74, 3.2, 1.5 ), //N 19
                new Point3D(- 0.2, - 3.98, 8.98 ), //Q 20
                new Point3D(- 0.1, - 1.93, 4.37), //R 21
                new Point3D( 10.41 ,5.16, 13.0 ), //S 22
                new Point3D( 10.52, 7.21 ,8.44), //T 23
                new Point3D(3.20, - 0.89 ,10.36), //U 24
                new Point3D(3.5, 1.25 ,5.79 ), //V 25
                new Point3D(7.0, 2.39, 11.83),//W 26
                new Point3D(7.30, 4.45, 7.21), //Z 27

                new Point3D(- 3.45, - 0.21, 10.32 ), // V 28
                new Point3D(- 6.9, 2.56, 11.75 ), // U 29
                new Point3D(-10.21 , 5.79, 13.12), // N 30
                     new Point3D(-3.3 , 1.19, 5.7), // T 31
                     new Point3D(-6.81 ,4.67, 7.15), //S 32
                new Point3D(- 10.11, 7.83, 8.52), //M 33
                     new Point3D( -3.16, 3.09, 1.3 ), //L 34
                     new Point3D( -6.72, 6.57, 2.76), //K 35
                     new Point3D(- 10.01 ,9.79, 4.11)}; //D 36

        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLUE), 0.10));

        scene.geometries.add(

               new Sphere(3, new Point3D(15, 0, 0))
                        .set_emission(new Color(java.awt.Color.GREEN)) // right down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Sphere(5, new Point3D(18, -18, 10))
                        .set_emission(new Color(java.awt.Color.RED)) // right down
                        .set_material(new Material().setKd(0.5).setKs(0.5).setShininess(30)),

                new Triangle(pnts[13], pnts[7], pnts[0])
                        .set_emission(new Color(java.awt.Color.BLUE)),

                new Triangle(pnts[0], pnts[8], pnts[7])
                        .set_emission(new Color(java.awt.Color.BLUE)), // left

                new Triangle(pnts[6], pnts[7], pnts[8])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // right

                new Triangle(pnts[8], pnts[9], pnts[6])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // right

                new Triangle(pnts[15], pnts[9], pnts[6])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[15], pnts[9], pnts[5])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[1], pnts[0], pnts[8])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // up

                new Triangle(pnts[1], pnts[8], pnts[10])
                        .set_emission(new Color(java.awt.Color.ORANGE)), //down

                new Triangle(pnts[8], pnts[9], pnts[10])
                        .set_emission(new Color(java.awt.Color.WHITE)), //down

                new Triangle(pnts[9], pnts[10], pnts[11])
                        .set_emission(new Color(java.awt.Color.WHITE)), // left

                new Triangle(pnts[9], pnts[5], pnts[11])
                        .set_emission(new Color(java.awt.Color.ORANGE )), // left

                new Triangle(pnts[4], pnts[5], pnts[11])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // right

                new Triangle(pnts[1], pnts[10], pnts[12])
                        .set_emission(new Color(java.awt.Color.BLUE)), // right

                new Triangle(pnts[2], pnts[12], pnts[10])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[2], pnts[11], pnts[10])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // up

                new Triangle(pnts[2], pnts[3], pnts[11])
                        .set_emission(new Color(java.awt.Color.orange)), // up

                new Triangle(pnts[3], pnts[4], pnts[11])
                        .set_emission(new Color(java.awt.Color.BLUE)), //down

                new Triangle(pnts[14], pnts[4], pnts[3])
                        .set_emission(new Color(java.awt.Color.BLUE)),//down

        //********************************************************************************///

        new Triangle(pnts[12], pnts[2], pnts[20])
                .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(pnts[20], pnts[2], pnts[24])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(pnts[3], pnts[2], pnts[24])
                        .set_emission(new Color(java.awt.Color.RED)), // right

                new Triangle(pnts[3], pnts[24], pnts[26])
                        .set_emission(new Color(java.awt.Color.RED)), // right

                new Triangle(pnts[3], pnts[14], pnts[26])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // up

                new Triangle(pnts[22], pnts[14], pnts[26])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // up

                new Triangle(pnts[21], pnts[20], pnts[24])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[21], pnts[24], pnts[25])
                        .set_emission(new Color(java.awt.Color.BLUE)), //down

                new Triangle(pnts[24], pnts[25], pnts[26])
                        .set_emission(new Color(java.awt.Color.WHITE)), //down

                new Triangle(pnts[27], pnts[25], pnts[26])
                        .set_emission(new Color(java.awt.Color.WHITE)), //down

                new Triangle(pnts[22], pnts[26], pnts[27])
                        .set_emission(new Color(java.awt.Color.GREEN)), // left

                new Triangle(pnts[23], pnts[22], pnts[27])
                        .set_emission(new Color(java.awt.Color.GREEN )), // left

                new Triangle(pnts[16], pnts[21], pnts[25])
                        .set_emission(new Color(java.awt.Color.WHITE)), // right

                new Triangle(pnts[16], pnts[19], pnts[25])
                        .set_emission(new Color(java.awt.Color.WHITE)), // right

                new Triangle(pnts[19], pnts[25], pnts[27])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[19], pnts[27], pnts[18])
                        .set_emission(new Color(java.awt.Color.BLUE)), // up

                new Triangle(pnts[23], pnts[27], pnts[18])
                        .set_emission(new Color(java.awt.Color.RED)), // up

                new Triangle(pnts[23], pnts[18], pnts[17])
                        .set_emission(new Color(java.awt.Color.RED)), //down
        //**********************************************************************************//

                new Triangle(pnts[13], pnts[29], pnts[30])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(pnts[13], pnts[0], pnts[29])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(pnts[0], pnts[28], pnts[29])
                        .set_emission(new Color(java.awt.Color.BLUE)), // right

                new Triangle(pnts[1], pnts[0], pnts[28])
                        .set_emission(new Color(java.awt.Color.BLUE)), // right

                new Triangle(pnts[1], pnts[20], pnts[28])
                        .set_emission(new Color(java.awt.Color.WHITE)), // up

                new Triangle(pnts[1], pnts[12], pnts[20])
                        .set_emission(new Color(java.awt.Color.WHITE)), // up

                new Triangle(pnts[30], pnts[32], pnts[33])
                        .set_emission(new Color(java.awt.Color.RED)), // up

                new Triangle(pnts[29], pnts[32], pnts[30])
                        .set_emission(new Color(java.awt.Color.RED)), //down

                new Triangle(pnts[29], pnts[31], pnts[32])
                        .set_emission(new Color(java.awt.Color.ORANGE)), //down

                new Triangle(pnts[29], pnts[31], pnts[28])
                        .set_emission(new Color(java.awt.Color.ORANGE)), // left

                new Triangle(pnts[28], pnts[21], pnts[31])
                        .set_emission(new Color(java.awt.Color.GREEN )), // left

                new Triangle(pnts[28], pnts[20], pnts[21])
                        .set_emission(new Color(java.awt.Color.GREEN)), // right

                new Triangle(pnts[32], pnts[33], pnts[35])
                        .set_emission(new Color(java.awt.Color.GREEN)), // right

                new Triangle(pnts[33], pnts[35], pnts[36])
                        .set_emission(new Color(java.awt.Color.GREEN)), // up

                new Triangle(pnts[32], pnts[34], pnts[35])
                        .set_emission(new Color(java.awt.Color.RED)), // up

                new Triangle(pnts[32], pnts[31], pnts[34])
                        .set_emission(new Color(java.awt.Color.RED)), // up

                new Triangle(pnts[16], pnts[31], pnts[34])
                        .set_emission(new Color(java.awt.Color.ORANGE)), //down

                new Triangle(pnts[31], pnts[16], pnts[21])
                        .set_emission(new Color(java.awt.Color.ORANGE)));//down

        scene._lights.add( //
                new SpotLight(new Color(700,600,600), new Point3D(40, 40, 115), new Vector(0,0,-5)) //
                        .setKl(4E-4).setKq(2E-5));

        ImageWriter imageWriter = new ImageWriter("picture level 2", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene));

        //here we do the multi ray
        render.renderImageForSuperSampling();
        render.writeToImage();
    }

    /**
     * Test rendering an image
     *
     * @author Dan
     */
    public static class TeapotTest {
        private final Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)) //
                .setDistance(1000).setViewPlaneSize(200, 200).setNumOfRays(9);
        private final Scene scene = new Scene("Test scene");

        private static final Color color = new Color(200, 0, 0);
        private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);

        private static Point3D[] pnts = new Point3D[] { null, //
                new Point3D(40.6266, 28.3457, -1.10804), //
                new Point3D(40.0714, 30.4443, -1.10804), //
                new Point3D(40.7155, 31.1438, -1.10804), //
                new Point3D(42.0257, 30.4443, -1.10804), //
                new Point3D(43.4692, 28.3457, -1.10804), //
                new Point3D(37.5425, 28.3457, 14.5117), //
                new Point3D(37.0303, 30.4443, 14.2938), //
                new Point3D(37.6244, 31.1438, 14.5466), //
                new Point3D(38.8331, 30.4443, 15.0609), //
                new Point3D(40.1647, 28.3457, 15.6274), //
                new Point3D(29.0859, 28.3457, 27.1468), //
                new Point3D(28.6917, 30.4443, 26.7527), //
                new Point3D(29.149, 31.1438, 27.2099), //
                new Point3D(30.0792, 30.4443, 28.1402), //
                new Point3D(31.1041, 28.3457, 29.165), //
                new Point3D(16.4508, 28.3457, 35.6034), //
                new Point3D(16.2329, 30.4443, 35.0912), //
                new Point3D(16.4857, 31.1438, 35.6853), //
                new Point3D(16.9999, 30.4443, 36.894), //
                new Point3D(17.5665, 28.3457, 38.2256), //
                new Point3D(0.831025, 28.3457, 38.6876), //
                new Point3D(0.831025, 30.4443, 38.1324), //
                new Point3D(0.831025, 31.1438, 38.7764), //
                new Point3D(0.831025, 30.4443, 40.0866), //
                new Point3D(0.831025, 28.3457, 41.5301), //
                new Point3D(-15.868, 28.3457, 35.6034), //
                new Point3D(-15.0262, 30.4443, 35.0912), //
                new Point3D(-14.9585, 31.1438, 35.6853), //
                new Point3D(-15.3547, 30.4443, 36.894), //
                new Point3D(-15.9044, 28.3457, 38.2256), //
                new Point3D(-28.3832, 28.3457, 27.1468), //
                new Point3D(-27.4344, 30.4443, 26.7527), //
                new Point3D(-27.6068, 31.1438, 27.2099), //
                new Point3D(-28.4322, 30.4443, 28.1402), //
                new Point3D(-29.4421, 28.3457, 29.165), //
                new Point3D(-36.2402, 28.3457, 14.5117), //
                new Point3D(-35.52, 30.4443, 14.2938), //
                new Point3D(-36.0073, 31.1438, 14.5466), //
                new Point3D(-37.1767, 30.4443, 15.0609), //
                new Point3D(-38.5027, 28.3457, 15.6274), //
                new Point3D(-38.9646, 28.3457, -1.10804), //
                new Point3D(-38.4094, 30.4443, -1.10804), //
                new Point3D(-39.0534, 31.1438, -1.10804), //
                new Point3D(-40.3636, 30.4443, -1.10804), //
                new Point3D(-41.8071, 28.3457, -1.10804), //
                new Point3D(-35.8804, 28.3457, -16.7278), //
                new Point3D(-35.3683, 30.4443, -16.5099), //
                new Point3D(-35.9624, 31.1438, -16.7627), //
                new Point3D(-37.1711, 30.4443, -17.2769), //
                new Point3D(-38.5027, 28.3457, -17.8435), //
                new Point3D(-27.4238, 28.3457, -29.3629), //
                new Point3D(-27.0297, 30.4443, -28.9687), //
                new Point3D(-27.4869, 31.1438, -29.426), //
                new Point3D(-28.4172, 30.4443, -30.3562), //
                new Point3D(-29.4421, 28.3457, -31.3811), //
                new Point3D(-14.7887, 28.3457, -37.8195), //
                new Point3D(-14.5708, 30.4443, -37.3073), //
                new Point3D(-14.8236, 31.1438, -37.9014), //
                new Point3D(-15.3379, 30.4443, -39.1101), //
                new Point3D(-15.9044, 28.3457, -40.4417), //
                new Point3D(0.831025, 28.3457, -40.9036), //
                new Point3D(0.831025, 30.4443, -40.3484), //
                new Point3D(0.831025, 31.1438, -40.9925), //
                new Point3D(0.831025, 30.4443, -42.3027), //
                new Point3D(0.831025, 28.3457, -43.7462), //
                new Point3D(16.4508, 28.3457, -37.8195), //
                new Point3D(16.2329, 30.4443, -37.3073), //
                new Point3D(16.4857, 31.1438, -37.9014), //
                new Point3D(16.9999, 30.4443, -39.1101), //
                new Point3D(17.5665, 28.3457, -40.4417), //
                new Point3D(29.0859, 28.3457, -29.3629), //
                new Point3D(28.6917, 30.4443, -28.9687), //
                new Point3D(29.149, 31.1438, -29.426), //
                new Point3D(30.0792, 30.4443, -30.3562), //
                new Point3D(31.1041, 28.3457, -31.3811), //
                new Point3D(37.5425, 28.3457, -16.7278), //
                new Point3D(37.0303, 30.4443, -16.5099), //
                new Point3D(37.6244, 31.1438, -16.7627), //
                new Point3D(38.8331, 30.4443, -17.2769), //
                new Point3D(40.1647, 28.3457, -17.8435), //
                new Point3D(48.6879, 17.1865, -1.10804), //
                new Point3D(53.2404, 6.22714, -1.10804), //
                new Point3D(56.4605, -4.33246, -1.10804), //
                new Point3D(57.6819, -14.2925, -1.10804), //
                new Point3D(44.979, 17.1865, 17.6758), //
                new Point3D(49.1787, 6.22714, 19.4626), //
                new Point3D(52.1492, -4.33246, 20.7265), //
                new Point3D(53.2759, -14.2925, 21.2059), //
                new Point3D(34.8094, 17.1865, 32.8703), //
                new Point3D(38.0417, 6.22714, 36.1026), //
                new Point3D(40.3279, -4.33246, 38.3889), //
                new Point3D(41.1951, -14.2925, 39.2561), //
                new Point3D(19.6148, 17.1865, 43.0399), //
                new Point3D(21.4017, 6.22714, 47.2396), //
                new Point3D(22.6656, -4.33246, 50.2101), //
                new Point3D(23.145, -14.2925, 51.3369), //
                new Point3D(0.831025, 17.1865, 46.7488), //
                new Point3D(0.831025, 6.22714, 51.3013), //
                new Point3D(0.831025, -4.33246, 54.5214), //
                new Point3D(0.831025, -14.2925, 55.7428), //
                new Point3D(-17.9528, 17.1865, 43.0399), //
                new Point3D(-19.7397, 6.22714, 47.2396), //
                new Point3D(-21.0035, -4.33246, 50.2101), //
                new Point3D(-21.4829, -14.2925, 51.3369), //
                new Point3D(-33.1474, 17.1865, 32.8703), //
                new Point3D(-36.3796, 6.22714, 36.1026), //
                new Point3D(-38.6659, -4.33246, 38.3889), //
                new Point3D(-39.5331, -14.2925, 39.2561), //
                new Point3D(-43.3169, 17.1865, 17.6758), //
                new Point3D(-47.5166, 6.22714, 19.4626), //
                new Point3D(-50.4871, -4.33246, 20.7265), //
                new Point3D(-51.6139, -14.2925, 21.2059), //
                new Point3D(-47.0258, 17.1865, -1.10804), //
                new Point3D(-51.5784, 6.22714, -1.10804), //
                new Point3D(-54.7984, -4.33246, -1.10804), //
                new Point3D(-56.0198, -14.2925, -1.10804), //
                new Point3D(-43.3169, 17.1865, -19.8919), //
                new Point3D(-47.5166, 6.22714, -21.6787), //
                new Point3D(-50.4871, -4.33246, -22.9426), //
                new Point3D(-51.6139, -14.2925, -23.422), //
                new Point3D(-33.1474, 17.1865, -35.0864), //
                new Point3D(-36.3796, 6.22714, -38.3187), //
                new Point3D(-38.6659, -4.33246, -40.6049), //
                new Point3D(-39.5331, -14.2925, -41.4721), //
                new Point3D(-17.9528, 17.1865, -45.256), //
                new Point3D(-19.7397, 6.22714, -49.4557), //
                new Point3D(-21.0035, -4.33246, -52.4262), //
                new Point3D(-21.4829, -14.2925, -53.5529), //
                new Point3D(0.831025, 17.1865, -48.9649), //
                new Point3D(0.831025, 6.22714, -53.5174), //
                new Point3D(0.831025, -4.33246, -56.7375), //
                new Point3D(0.831025, -14.2925, -57.9589), //
                new Point3D(19.6148, 17.1865, -45.256), //
                new Point3D(21.4017, 6.22714, -49.4557), //
                new Point3D(22.6656, -4.33246, -52.4262), //
                new Point3D(23.145, -14.2925, -53.5529), //
                new Point3D(34.8094, 17.1865, -35.0864), //
                new Point3D(38.0417, 6.22714, -38.3187), //
                new Point3D(40.3279, -4.33246, -40.6049), //
                new Point3D(41.1951, -14.2925, -41.4721), //
                new Point3D(44.979, 17.1865, -19.8919), //
                new Point3D(49.1787, 6.22714, -21.6787), //
                new Point3D(52.1492, -4.33246, -22.9426), //
                new Point3D(53.2759, -14.2925, -23.422), //
                new Point3D(55.4611, -22.7202, -1.10804), //
                new Point3D(50.5755, -28.9493, -1.10804), //
                new Point3D(45.6899, -33.1798, -1.10804), //
                new Point3D(43.4692, -35.6115, -1.10804), //
                new Point3D(51.2273, -22.7202, 20.3343), //
                new Point3D(46.7203, -28.9493, 18.4167), //
                new Point3D(42.2133, -33.1798, 16.4991), //
                new Point3D(40.1647, -35.6115, 15.6274), //
                new Point3D(39.6184, -22.7202, 37.6793), //
                new Point3D(36.1496, -28.9493, 34.2106), //
                new Point3D(32.6808, -33.1798, 30.7418), //
                new Point3D(31.1041, -35.6115, 29.165), //
                new Point3D(22.2733, -22.7202, 49.2882), //
                new Point3D(20.3557, -28.9493, 44.7813), //
                new Point3D(18.4381, -33.1798, 40.2743), //
                new Point3D(17.5665, -35.6115, 38.2256), //
                new Point3D(0.831025, -22.7202, 53.5221), //
                new Point3D(0.831025, -28.9493, 48.6365), //
                new Point3D(0.831025, -33.1798, 43.7508), //
                new Point3D(0.831025, -35.6115, 41.5301), //
                new Point3D(-20.6113, -22.7202, 49.2882), //
                new Point3D(-18.6937, -28.9493, 44.7813), //
                new Point3D(-16.7761, -33.1798, 40.2743), //
                new Point3D(-15.9044, -35.6115, 38.2256), //
                new Point3D(-37.9564, -22.7202, 37.6793), //
                new Point3D(-34.4876, -28.9493, 34.2106), //
                new Point3D(-31.0188, -33.1798, 30.7418), //
                new Point3D(-29.4421, -35.6115, 29.165), //
                new Point3D(-49.5653, -22.7202, 20.3343), //
                new Point3D(-45.0583, -28.9493, 18.4167), //
                new Point3D(-40.5513, -33.1798, 16.4991), //
                new Point3D(-38.5027, -35.6115, 15.6274), //
                new Point3D(-53.7991, -22.7202, -1.10804), //
                new Point3D(-48.9135, -28.9493, -1.10804), //
                new Point3D(-44.0279, -33.1798, -1.10804), //
                new Point3D(-41.8071, -35.6115, -1.10804), //
                new Point3D(-49.5653, -22.7202, -22.5504), //
                new Point3D(-45.0583, -28.9493, -20.6327), //
                new Point3D(-40.5513, -33.1798, -18.7151), //
                new Point3D(-38.5027, -35.6115, -17.8435), //
                new Point3D(-37.9564, -22.7202, -39.8954), //
                new Point3D(-34.4876, -28.9493, -36.4266), //
                new Point3D(-31.0188, -33.1798, -32.9578), //
                new Point3D(-29.4421, -35.6115, -31.3811), //
                new Point3D(-20.6113, -22.7202, -51.5043), //
                new Point3D(-18.6937, -28.9493, -46.9973), //
                new Point3D(-16.7761, -33.1798, -42.4903), //
                new Point3D(-15.9044, -35.6115, -40.4417), //
                new Point3D(0.831025, -22.7202, -55.7382), //
                new Point3D(0.831025, -28.9493, -50.8525), //
                new Point3D(0.831025, -33.1798, -45.9669), //
                new Point3D(0.831025, -35.6115, -43.7462), //
                new Point3D(22.2733, -22.7202, -51.5043), //
                new Point3D(20.3557, -28.9493, -46.9973), //
                new Point3D(18.4381, -33.1798, -42.4903), //
                new Point3D(17.5665, -35.6115, -40.4417), //
                new Point3D(39.6184, -22.7202, -39.8954), //
                new Point3D(36.1496, -28.9493, -36.4266), //
                new Point3D(32.6808, -33.1798, -32.9578), //
                new Point3D(31.1041, -35.6115, -31.3811), //
                new Point3D(51.2273, -22.7202, -22.5504), //
                new Point3D(46.7203, -28.9493, -20.6327), //
                new Point3D(42.2133, -33.1798, -18.7151), //
                new Point3D(40.1647, -35.6115, -17.8435), //
                new Point3D(42.5031, -37.1772, -1.10804), //
                new Point3D(37.3399, -38.5429, -1.10804), //
                new Point3D(24.5818, -39.5089, -1.10804), //
                new Point3D(0.831025, -39.8754, -1.10804), //
                new Point3D(39.2736, -37.1772, 15.2483), //
                new Point3D(34.5105, -38.5429, 13.2217), //
                new Point3D(22.7411, -39.5089, 8.21414), //
                new Point3D(30.4182, -37.1772, 28.4792), //
                new Point3D(26.7523, -38.5429, 24.8133), //
                new Point3D(17.6941, -39.5089, 15.755), //
                new Point3D(17.1873, -37.1772, 37.3345), //
                new Point3D(15.1608, -38.5429, 32.5714), //
                new Point3D(10.1532, -39.5089, 20.8021), //
                new Point3D(0.831025, -37.1772, 40.5641), //
                new Point3D(0.831025, -38.5429, 35.4009), //
                new Point3D(0.831025, -39.5089, 22.6427), //
                new Point3D(-15.5253, -37.1772, 37.3345), //
                new Point3D(-13.4987, -38.5429, 32.5714), //
                new Point3D(-8.49115, -39.5089, 20.8021), //
                new Point3D(-28.7562, -37.1772, 28.4792), //
                new Point3D(-25.0903, -38.5429, 24.8133), //
                new Point3D(-16.032, -39.5089, 15.755), //
                new Point3D(-37.6115, -37.1772, 15.2483), //
                new Point3D(-32.8484, -38.5429, 13.2217), //
                new Point3D(-21.0791, -39.5089, 8.21414), //
                new Point3D(-40.8411, -37.1772, -1.10804), //
                new Point3D(-35.6779, -38.5429, -1.10804), //
                new Point3D(-22.9198, -39.5089, -1.10804), //
                new Point3D(-37.6115, -37.1772, -17.4643), //
                new Point3D(-32.8484, -38.5429, -15.4378), //
                new Point3D(-21.0791, -39.5089, -10.4302), //
                new Point3D(-28.7562, -37.1772, -30.6952), //
                new Point3D(-25.0903, -38.5429, -27.0294), //
                new Point3D(-16.032, -39.5089, -17.9711), //
                new Point3D(-15.5253, -37.1772, -39.5506), //
                new Point3D(-13.4987, -38.5429, -34.7875), //
                new Point3D(-8.49115, -39.5089, -23.0181), //
                new Point3D(0.831025, -37.1772, -42.7802), //
                new Point3D(0.831025, -38.5429, -37.6169), //
                new Point3D(0.831025, -39.5089, -24.8588), //
                new Point3D(17.1873, -37.1772, -39.5506), //
                new Point3D(15.1608, -38.5429, -34.7875), //
                new Point3D(10.1532, -39.5089, -23.0181), //
                new Point3D(30.4182, -37.1772, -30.6952), //
                new Point3D(26.7523, -38.5429, -27.0294), //
                new Point3D(17.6941, -39.5089, -17.9711), //
                new Point3D(39.2736, -37.1772, -17.4643), //
                new Point3D(34.5105, -38.5429, -15.4378), //
                new Point3D(22.7411, -39.5089, -10.4302), //
                new Point3D(-44.6497, 17.6861, -1.10804), //
                new Point3D(-57.9297, 17.5862, -1.10804), //
                new Point3D(-67.7453, 16.8867, -1.10804), //
                new Point3D(-73.8301, 14.9879, -1.10804), //
                new Point3D(-75.9176, 11.2904, -1.10804), //
                new Point3D(-44.2055, 18.6855, 3.68876), //
                new Point3D(-58.3252, 18.5699, 3.68876), //
                new Point3D(-68.6891, 17.7611, 3.68876), //
                new Point3D(-75.0724, 15.5657, 3.68876), //
                new Point3D(-77.2501, 11.2904, 3.68876), //
                new Point3D(-43.2284, 20.884, 5.28769), //
                new Point3D(-59.1955, 20.7341, 5.28769), //
                new Point3D(-70.7655, 19.6848, 5.28769), //
                new Point3D(-77.8053, 16.8367, 5.28769), //
                new Point3D(-80.1814, 11.2904, 5.28769), //
                new Point3D(-42.2513, 23.0825, 3.68876), //
                new Point3D(-60.0657, 22.8983, 3.68876), //
                new Point3D(-72.8419, 21.6085, 3.68876), //
                new Point3D(-80.5381, 18.1077, 3.68876), //
                new Point3D(-83.1128, 11.2904, 3.68876), //
                new Point3D(-41.8071, 24.0819, -1.10804), //
                new Point3D(-60.4613, 23.882, -1.10804), //
                new Point3D(-73.7857, 22.4829, -1.10804), //
                new Point3D(-81.7804, 18.6855, -1.10804), //
                new Point3D(-84.4453, 11.2904, -1.10804), //
                new Point3D(-42.2513, 23.0825, -5.90483), //
                new Point3D(-60.0657, 22.8983, -5.90483), //
                new Point3D(-72.8419, 21.6085, -5.90483), //
                new Point3D(-80.5381, 18.1077, -5.90483), //
                new Point3D(-83.1128, 11.2904, -5.90483), //
                new Point3D(-43.2284, 20.884, -7.50376), //
                new Point3D(-59.1955, 20.7341, -7.50376), //
                new Point3D(-70.7655, 19.6848, -7.50376), //
                new Point3D(-77.8053, 16.8367, -7.50376), //
                new Point3D(-80.1814, 11.2904, -7.50376), //
                new Point3D(-44.2055, 18.6855, -5.90483), //
                new Point3D(-58.3252, 18.5699, -5.90483), //
                new Point3D(-68.6891, 17.7611, -5.90483), //
                new Point3D(-75.0724, 15.5657, -5.90483), //
                new Point3D(-77.2501, 11.2904, -5.90483), //
                new Point3D(-74.8073, 5.4943, -1.10804), //
                new Point3D(-71.2985, -1.50103, -1.10804), //
                new Point3D(-65.1248, -8.49634, -1.10804), //
                new Point3D(-56.0198, -14.2925, -1.10804), //
                new Point3D(-76.0183, 4.93477, 3.68876), //
                new Point3D(-72.159, -2.35462, 3.68876), //
                new Point3D(-65.4267, -9.55033, 3.68876), //
                new Point3D(-55.5757, -15.6249, 3.68876), //
                new Point3D(-78.6824, 3.70383, 5.28769), //
                new Point3D(-74.0522, -4.23253, 5.28769), //
                new Point3D(-66.0909, -11.8691, 5.28769), //
                new Point3D(-54.5986, -18.5563, 5.28769), //
                new Point3D(-81.3466, 2.47288, 3.68876), //
                new Point3D(-75.9454, -6.11044, 3.68876), //
                new Point3D(-66.755, -14.1878, 3.68876), //
                new Point3D(-53.6214, -21.4877, 3.68876), //
                new Point3D(-82.5576, 1.91336, -1.10804), //
                new Point3D(-76.8059, -6.96404, -1.10804), //
                new Point3D(-67.0569, -15.2418, -1.10804), //
                new Point3D(-53.1773, -22.8201, -1.10804), //
                new Point3D(-81.3466, 2.47288, -5.90483), //
                new Point3D(-75.9454, -6.11044, -5.90483), //
                new Point3D(-66.755, -14.1878, -5.90483), //
                new Point3D(-53.6214, -21.4877, -5.90483), //
                new Point3D(-78.6824, 3.70383, -7.50376), //
                new Point3D(-74.0522, -4.23253, -7.50376), //
                new Point3D(-66.0909, -11.8691, -7.50376), //
                new Point3D(-54.5986, -18.5563, -7.50376), //
                new Point3D(-76.0183, 4.93477, -5.90483), //
                new Point3D(-72.159, -2.35462, -5.90483), //
                new Point3D(-65.4267, -9.55033, -5.90483), //
                new Point3D(-55.5757, -15.6249, -5.90483), //
                new Point3D(49.1543, 0.630882, -1.10804), //
                new Point3D(62.7896, 3.76212, -1.10804), //
                new Point3D(68.6967, 11.2904, -1.10804), //
                new Point3D(71.939, 20.4176, -1.10804), //
                new Point3D(77.5797, 28.3457, -1.10804), //
                new Point3D(49.1543, -3.03333, 9.4449), //
                new Point3D(63.8305, 1.04519, 8.42059), //
                new Point3D(70.0292, 9.70814, 6.1671), //
                new Point3D(73.5629, 19.8451, 3.91361), //
                new Point3D(80.2446, 28.3457, 2.88929), //
                new Point3D(49.1543, -11.0946, 12.9626), //
                new Point3D(66.1207, -4.93206, 11.5968), //
                new Point3D(72.9605, 6.22714, 8.59214), //
                new Point3D(77.1355, 18.5855, 5.58749), //
                new Point3D(86.1073, 28.3457, 4.22173), //
                new Point3D(49.1543, -19.1559, 9.4449), //
                new Point3D(68.4108, -10.9093, 8.42059), //
                new Point3D(75.8919, 2.74614, 6.1671), //
                new Point3D(80.7081, 17.326, 3.91361), //
                new Point3D(91.97, 28.3457, 2.88929), //
                new Point3D(49.1543, -22.8201, -1.10804), //
                new Point3D(69.4518, -13.6262, -1.10804), //
                new Point3D(77.2244, 1.16386, -1.10804), //
                new Point3D(82.3321, 16.7534, -1.10804), //
                new Point3D(94.6349, 28.3457, -1.10804), //
                new Point3D(49.1543, -19.1559, -11.661), //
                new Point3D(68.4108, -10.9093, -10.6367), //
                new Point3D(75.8919, 2.74614, -8.38317), //
                new Point3D(80.7081, 17.326, -6.12968), //
                new Point3D(91.97, 28.3457, -5.10536), //
                new Point3D(49.1543, -11.0946, -15.1786), //
                new Point3D(66.1207, -4.93206, -13.8129), //
                new Point3D(72.9605, 6.22714, -10.8082), //
                new Point3D(77.1355, 18.5855, -7.80356), //
                new Point3D(86.1073, 28.3457, -6.4378), //
                new Point3D(49.1543, -3.03333, -11.661), //
                new Point3D(63.8305, 1.04519, -10.6367), //
                new Point3D(70.0292, 9.70814, -8.38317), //
                new Point3D(73.5629, 19.8451, -6.12968), //
                new Point3D(80.2446, 28.3457, -5.10536), //
                new Point3D(79.6227, 29.5449, -1.10804), //
                new Point3D(81.1329, 29.9446, -1.10804), //
                new Point3D(81.577, 29.5449, -1.10804), //
                new Point3D(80.4222, 28.3457, -1.10804), //
                new Point3D(82.4767, 29.6034, 2.63946), //
                new Point3D(83.8116, 30.0383, 2.08983), //
                new Point3D(83.8515, 29.6268, 1.54019), //
                new Point3D(82.1988, 28.3457, 1.29036), //
                new Point3D(88.7555, 29.7322, 3.88862), //
                new Point3D(89.7049, 30.2444, 3.15578), //
                new Point3D(88.8555, 29.8072, 2.42294), //
                new Point3D(86.1073, 28.3457, 2.08983), //
                new Point3D(95.0343, 29.8611, 2.63946), //
                new Point3D(95.5982, 30.4505, 2.08983), //
                new Point3D(93.8594, 29.9875, 1.54019), //
                new Point3D(90.0158, 28.3457, 1.29036), //
                new Point3D(97.8883, 29.9196, -1.10804), //
                new Point3D(98.2769, 30.5442, -1.10804), //
                new Point3D(96.1339, 30.0695, -1.10804), //
                new Point3D(91.7924, 28.3457, -1.10804), //
                new Point3D(95.0343, 29.8611, -4.85553), //
                new Point3D(95.5982, 30.4505, -4.3059), //
                new Point3D(93.8594, 29.9875, -3.75626), //
                new Point3D(90.0158, 28.3457, -3.50643), //
                new Point3D(88.7555, 29.7322, -6.10469), //
                new Point3D(89.7049, 30.2444, -5.37185), //
                new Point3D(88.8555, 29.8072, -4.63901), //
                new Point3D(86.1073, 28.3457, -4.3059), //
                new Point3D(82.4767, 29.6034, -4.85553), //
                new Point3D(83.8116, 30.0383, -4.3059), //
                new Point3D(83.8515, 29.6268, -3.75626), //
                new Point3D(82.1988, 28.3457, -3.50643), //
                new Point3D(0.831025, 49.6647, -1.10804), //
                new Point3D(10.5134, 48.2657, -1.10804), //
                new Point3D(10.0693, 44.868, -1.10804), //
                new Point3D(6.42728, 40.6708, -1.10804), //
                new Point3D(6.51611, 36.8733, -1.10804), //
                new Point3D(9.76642, 48.2657, 2.70243), //
                new Point3D(9.35632, 44.868, 2.52698), //
                new Point3D(5.9947, 40.6708, 1.09187), //
                new Point3D(6.07552, 36.8733, 1.12336), //
                new Point3D(7.71453, 48.2657, 5.77547), //
                new Point3D(7.39819, 44.868, 5.45913), //
                new Point3D(4.80736, 40.6708, 2.8683), //
                new Point3D(4.86744, 36.8733, 2.92838), //
                new Point3D(4.64149, 48.2657, 7.82736), //
                new Point3D(4.46604, 44.868, 7.41726), //
                new Point3D(3.03093, 40.6708, 4.05564), //
                new Point3D(3.06242, 36.8733, 4.13646), //
                new Point3D(0.831025, 48.2657, 8.57438), //
                new Point3D(0.831025, 44.868, 8.13023), //
                new Point3D(0.831025, 40.6708, 4.48822), //
                new Point3D(0.831025, 36.8733, 4.57705), //
                new Point3D(-2.97944, 48.2657, 7.82736), //
                new Point3D(-2.80399, 44.868, 7.41726), //
                new Point3D(-1.36888, 40.6708, 4.05564), //
                new Point3D(-1.40037, 36.8733, 4.13646), //
                new Point3D(-6.05248, 48.2657, 5.77547), //
                new Point3D(-5.73614, 44.868, 5.45913), //
                new Point3D(-3.14531, 40.6708, 2.8683), //
                new Point3D(-3.20539, 36.8733, 2.92838), //
                new Point3D(-8.10437, 48.2657, 2.70243), //
                new Point3D(-7.69427, 44.868, 2.52698), //
                new Point3D(-4.33265, 40.6708, 1.09187), //
                new Point3D(-4.41347, 36.8733, 1.12336), //
                new Point3D(-8.85139, 48.2657, -1.10804), //
                new Point3D(-8.40724, 44.868, -1.10804), //
                new Point3D(-4.76523, 40.6708, -1.10804), //
                new Point3D(-4.85406, 36.8733, -1.10804), //
                new Point3D(-8.10437, 48.2657, -4.9185), //
                new Point3D(-7.69427, 44.868, -4.74305), //
                new Point3D(-4.33265, 40.6708, -3.30794), //
                new Point3D(-4.41347, 36.8733, -3.33943), //
                new Point3D(-6.05248, 48.2657, -7.99154), //
                new Point3D(-5.73614, 44.868, -7.6752), //
                new Point3D(-3.14531, 40.6708, -5.08437), //
                new Point3D(-3.20539, 36.8733, -5.14445), //
                new Point3D(-2.97944, 48.2657, -10.0434), //
                new Point3D(-2.80399, 44.868, -9.63333), //
                new Point3D(-1.36888, 40.6708, -6.27171), //
                new Point3D(-1.40037, 36.8733, -6.35253), //
                new Point3D(0.831025, 48.2657, -10.7904), //
                new Point3D(0.831025, 44.868, -10.3463), //
                new Point3D(0.831025, 40.6708, -6.70429), //
                new Point3D(0.831025, 36.8733, -6.79312), //
                new Point3D(4.64149, 48.2657, -10.0434), //
                new Point3D(4.46604, 44.868, -9.63333), //
                new Point3D(3.03093, 40.6708, -6.27171), //
                new Point3D(3.06242, 36.8733, -6.35253), //
                new Point3D(7.71453, 48.2657, -7.99154), //
                new Point3D(7.39819, 44.868, -7.6752), //
                new Point3D(4.80736, 40.6708, -5.08437), //
                new Point3D(4.86744, 36.8733, -5.14445), //
                new Point3D(9.76642, 48.2657, -4.9185), //
                new Point3D(9.35632, 44.868, -4.74305), //
                new Point3D(5.9947, 40.6708, -3.30794), //
                new Point3D(6.07552, 36.8733, -3.33943), //
                new Point3D(13.8001, 34.3417, -1.10804), //
                new Point3D(24.282, 32.6095, -1.10804), //
                new Point3D(33.6979, 30.8773, -1.10804), //
                new Point3D(37.7841, 28.3457, -1.10804), //
                new Point3D(12.795, 34.3417, 3.98234), //
                new Point3D(22.4646, 32.6095, 8.09647), //
                new Point3D(31.1507, 30.8773, 11.7922), //
                new Point3D(34.9202, 28.3457, 13.396), //
                new Point3D(10.0391, 34.3417, 8.10003), //
                new Point3D(17.4812, 32.6095, 15.5422), //
                new Point3D(24.1665, 30.8773, 22.2275), //
                new Point3D(27.0677, 28.3457, 25.1286), //
                new Point3D(5.9214, 34.3417, 10.856), //
                new Point3D(10.0355, 32.6095, 20.5255), //
                new Point3D(13.7313, 30.8773, 29.2117), //
                new Point3D(15.3351, 28.3457, 32.9812), //
                new Point3D(0.831025, 34.3417, 11.8611), //
                new Point3D(0.831025, 32.6095, 22.3429), //
                new Point3D(0.831025, 30.8773, 31.7589), //
                new Point3D(0.831025, 28.3457, 35.845), //
                new Point3D(-4.25935, 34.3417, 10.856), //
                new Point3D(-8.37348, 32.6095, 20.5255), //
                new Point3D(-12.0692, 30.8773, 29.2117), //
                new Point3D(-13.673, 28.3457, 32.9812), //
                new Point3D(-8.37704, 34.3417, 8.10003), //
                new Point3D(-15.8192, 32.6095, 15.5422), //
                new Point3D(-22.5045, 30.8773, 22.2275), //
                new Point3D(-25.4056, 28.3457, 25.1286), //
                new Point3D(-11.133, 34.3417, 3.98234), //
                new Point3D(-20.8025, 32.6095, 8.09647), //
                new Point3D(-29.4887, 30.8773, 11.7922), //
                new Point3D(-33.2582, 28.3457, 13.396), //
                new Point3D(-12.1381, 34.3417, -1.10804), //
                new Point3D(-22.62, 32.6095, -1.10804), //
                new Point3D(-32.0359, 30.8773, -1.10804), //
                new Point3D(-36.122, 28.3457, -1.10804), //
                new Point3D(-11.133, 34.3417, -6.19841), //
                new Point3D(-20.8025, 32.6095, -10.3125), //
                new Point3D(-29.4887, 30.8773, -14.0083), //
                new Point3D(-33.2582, 28.3457, -15.6121), //
                new Point3D(-8.37704, 34.3417, -10.3161), //
                new Point3D(-15.8192, 32.6095, -17.7582), //
                new Point3D(-22.5045, 30.8773, -24.4435), //
                new Point3D(-25.4056, 28.3457, -27.3447), //
                new Point3D(-4.25935, 34.3417, -13.072), //
                new Point3D(-8.37348, 32.6095, -22.7416), //
                new Point3D(-12.0692, 30.8773, -31.4277), //
                new Point3D(-13.673, 28.3457, -35.1972), //
                new Point3D(0.831025, 34.3417, -14.0771), //
                new Point3D(0.831025, 32.6095, -24.559), //
                new Point3D(0.831025, 30.8773, -33.9749), //
                new Point3D(0.831025, 28.3457, -38.0611), //
                new Point3D(5.9214, 34.3417, -13.072), //
                new Point3D(10.0355, 32.6095, -22.7416), //
                new Point3D(13.7313, 30.8773, -31.4277), //
                new Point3D(15.3351, 28.3457, -35.1972), //
                new Point3D(10.0391, 34.3417, -10.3161), //
                new Point3D(17.4812, 32.6095, -17.7582), //
                new Point3D(24.1665, 30.8773, -24.4435), //
                new Point3D(27.0677, 28.3457, -27.3447), //
                new Point3D(12.795, 34.3417, -6.19841), //
                new Point3D(22.4646, 32.6095, -10.3125), //
                new Point3D(31.1507, 30.8773, -14.0083), //
                new Point3D(34.8094, 17.1865, -35.0864) //
        };

        /**
         * Produce a scene with a 3D model and render it into a png image
         */
        @Test
        public void teapot1() {
            scene.geometries.add( //
                    new Triangle(pnts[7], pnts[6], pnts[1]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[1], pnts[2], pnts[7]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[8], pnts[7], pnts[2]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[2], pnts[3], pnts[8]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[9], pnts[8], pnts[3]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[3], pnts[4], pnts[9]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[10], pnts[9], pnts[4]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[4], pnts[5], pnts[10]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[12], pnts[11], pnts[6]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[6], pnts[7], pnts[12]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[13], pnts[12], pnts[7]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[7], pnts[8], pnts[13]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[14], pnts[13], pnts[8]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[8], pnts[9], pnts[14]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[15], pnts[14], pnts[9]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[9], pnts[10], pnts[15]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[17], pnts[16], pnts[11]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[11], pnts[12], pnts[17]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[18], pnts[17], pnts[12]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[12], pnts[13], pnts[18]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[19], pnts[18], pnts[13]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[13], pnts[14], pnts[19]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[20], pnts[19], pnts[14]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[14], pnts[15], pnts[20]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[22], pnts[21], pnts[16]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[16], pnts[17], pnts[22]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[23], pnts[22], pnts[17]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[17], pnts[18], pnts[23]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[24], pnts[23], pnts[18]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[18], pnts[19], pnts[24]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[25], pnts[24], pnts[19]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[19], pnts[20], pnts[25]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[27], pnts[26], pnts[21]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[21], pnts[22], pnts[27]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[28], pnts[27], pnts[22]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[22], pnts[23], pnts[28]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[29], pnts[28], pnts[23]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[23], pnts[24], pnts[29]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[30], pnts[29], pnts[24]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[24], pnts[25], pnts[30]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[32], pnts[31], pnts[26]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[26], pnts[27], pnts[32]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[33], pnts[32], pnts[27]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[27], pnts[28], pnts[33]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[34], pnts[33], pnts[28]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[28], pnts[29], pnts[34]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[35], pnts[34], pnts[29]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[29], pnts[30], pnts[35]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[37], pnts[36], pnts[31]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[31], pnts[32], pnts[37]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[38], pnts[37], pnts[32]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[32], pnts[33], pnts[38]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[39], pnts[38], pnts[33]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[33], pnts[34], pnts[39]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[40], pnts[39], pnts[34]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[34], pnts[35], pnts[40]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[42], pnts[41], pnts[36]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[36], pnts[37], pnts[42]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[43], pnts[42], pnts[37]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[37], pnts[38], pnts[43]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[44], pnts[43], pnts[38]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[38], pnts[39], pnts[44]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[45], pnts[44], pnts[39]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[39], pnts[40], pnts[45]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[47], pnts[46], pnts[41]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[41], pnts[42], pnts[47]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[48], pnts[47], pnts[42]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[42], pnts[43], pnts[48]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[49], pnts[48], pnts[43]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[43], pnts[44], pnts[49]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[50], pnts[49], pnts[44]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[44], pnts[45], pnts[50]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[52], pnts[51], pnts[46]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[46], pnts[47], pnts[52]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[53], pnts[52], pnts[47]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[47], pnts[48], pnts[53]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[54], pnts[53], pnts[48]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[48], pnts[49], pnts[54]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[55], pnts[54], pnts[49]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[49], pnts[50], pnts[55]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[57], pnts[56], pnts[51]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[51], pnts[52], pnts[57]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[58], pnts[57], pnts[52]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[52], pnts[53], pnts[58]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[59], pnts[58], pnts[53]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[53], pnts[54], pnts[59]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[60], pnts[59], pnts[54]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[54], pnts[55], pnts[60]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[62], pnts[61], pnts[56]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[56], pnts[57], pnts[62]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[63], pnts[62], pnts[57]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[57], pnts[58], pnts[63]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[64], pnts[63], pnts[58]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[58], pnts[59], pnts[64]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[65], pnts[64], pnts[59]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[59], pnts[60], pnts[65]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[67], pnts[66], pnts[61]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[61], pnts[62], pnts[67]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[68], pnts[67], pnts[62]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[62], pnts[63], pnts[68]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[69], pnts[68], pnts[63]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[63], pnts[64], pnts[69]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[70], pnts[69], pnts[64]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[64], pnts[65], pnts[70]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[72], pnts[71], pnts[66]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[66], pnts[67], pnts[72]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[73], pnts[72], pnts[67]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[67], pnts[68], pnts[73]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[74], pnts[73], pnts[68]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[68], pnts[69], pnts[74]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[75], pnts[74], pnts[69]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[69], pnts[70], pnts[75]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[77], pnts[76], pnts[71]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[71], pnts[72], pnts[77]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[78], pnts[77], pnts[72]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[72], pnts[73], pnts[78]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[79], pnts[78], pnts[73]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[73], pnts[74], pnts[79]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[80], pnts[79], pnts[74]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[74], pnts[75], pnts[80]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[2], pnts[1], pnts[76]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[76], pnts[77], pnts[2]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[3], pnts[2], pnts[77]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[77], pnts[78], pnts[3]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[4], pnts[3], pnts[78]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[78], pnts[79], pnts[4]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[5], pnts[4], pnts[79]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[79], pnts[80], pnts[5]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[85], pnts[10], pnts[5]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[5], pnts[81], pnts[85]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[86], pnts[85], pnts[81]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[81], pnts[82], pnts[86]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[87], pnts[86], pnts[82]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[82], pnts[83], pnts[87]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[88], pnts[87], pnts[83]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[83], pnts[84], pnts[88]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[89], pnts[15], pnts[10]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[10], pnts[85], pnts[89]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[90], pnts[89], pnts[85]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[85], pnts[86], pnts[90]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[91], pnts[90], pnts[86]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[86], pnts[87], pnts[91]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[92], pnts[91], pnts[87]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[87], pnts[88], pnts[92]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[93], pnts[20], pnts[15]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[15], pnts[89], pnts[93]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[94], pnts[93], pnts[89]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[89], pnts[90], pnts[94]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[95], pnts[94], pnts[90]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[90], pnts[91], pnts[95]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[96], pnts[95], pnts[91]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[91], pnts[92], pnts[96]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[97], pnts[25], pnts[20]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[20], pnts[93], pnts[97]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[98], pnts[97], pnts[93]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[93], pnts[94], pnts[98]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[99], pnts[98], pnts[94]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[94], pnts[95], pnts[99]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[100], pnts[99], pnts[95]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[95], pnts[96], pnts[100]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[101], pnts[30], pnts[25]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[25], pnts[97], pnts[101]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[102], pnts[101], pnts[97]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[97], pnts[98], pnts[102]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[103], pnts[102], pnts[98]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[98], pnts[99], pnts[103]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[104], pnts[103], pnts[99]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[99], pnts[100], pnts[104]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[105], pnts[35], pnts[30]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[30], pnts[101], pnts[105]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[106], pnts[105], pnts[101]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[101], pnts[102], pnts[106]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[107], pnts[106], pnts[102]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[102], pnts[103], pnts[107]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[108], pnts[107], pnts[103]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[103], pnts[104], pnts[108]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[109], pnts[40], pnts[35]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[35], pnts[105], pnts[109]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[110], pnts[109], pnts[105]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[105], pnts[106], pnts[110]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[111], pnts[110], pnts[106]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[106], pnts[107], pnts[111]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[112], pnts[111], pnts[107]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[107], pnts[108], pnts[112]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[113], pnts[45], pnts[40]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[40], pnts[109], pnts[113]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[114], pnts[113], pnts[109]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[109], pnts[110], pnts[114]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[115], pnts[114], pnts[110]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[110], pnts[111], pnts[115]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[116], pnts[115], pnts[111]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[111], pnts[112], pnts[116]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[117], pnts[50], pnts[45]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[45], pnts[113], pnts[117]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[118], pnts[117], pnts[113]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[113], pnts[114], pnts[118]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[119], pnts[118], pnts[114]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[114], pnts[115], pnts[119]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[120], pnts[119], pnts[115]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[115], pnts[116], pnts[120]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[121], pnts[55], pnts[50]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[50], pnts[117], pnts[121]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[122], pnts[121], pnts[117]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[117], pnts[118], pnts[122]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[123], pnts[122], pnts[118]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[118], pnts[119], pnts[123]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[124], pnts[123], pnts[119]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[119], pnts[120], pnts[124]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[125], pnts[60], pnts[55]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[55], pnts[121], pnts[125]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[126], pnts[125], pnts[121]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[121], pnts[122], pnts[126]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[127], pnts[126], pnts[122]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[122], pnts[123], pnts[127]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[128], pnts[127], pnts[123]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[123], pnts[124], pnts[128]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[129], pnts[65], pnts[60]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[60], pnts[125], pnts[129]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[130], pnts[129], pnts[125]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[125], pnts[126], pnts[130]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[131], pnts[130], pnts[126]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[126], pnts[127], pnts[131]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[132], pnts[131], pnts[127]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[127], pnts[128], pnts[132]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[133], pnts[70], pnts[65]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[65], pnts[129], pnts[133]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[134], pnts[133], pnts[129]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[129], pnts[130], pnts[134]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[135], pnts[134], pnts[130]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[130], pnts[131], pnts[135]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[136], pnts[135], pnts[131]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[131], pnts[132], pnts[136]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[137], pnts[75], pnts[70]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[70], pnts[133], pnts[137]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[138], pnts[137], pnts[133]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[133], pnts[134], pnts[138]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[139], pnts[138], pnts[134]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[134], pnts[135], pnts[139]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[140], pnts[139], pnts[135]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[135], pnts[136], pnts[140]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[141], pnts[80], pnts[75]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[75], pnts[137], pnts[141]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[142], pnts[141], pnts[137]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[137], pnts[138], pnts[142]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[143], pnts[142], pnts[138]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[138], pnts[139], pnts[143]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[144], pnts[143], pnts[139]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[139], pnts[140], pnts[144]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[81], pnts[5], pnts[80]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[80], pnts[141], pnts[81]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[82], pnts[81], pnts[141]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[141], pnts[142], pnts[82]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[83], pnts[82], pnts[142]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[142], pnts[143], pnts[83]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[84], pnts[83], pnts[143]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[143], pnts[144], pnts[84]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[149], pnts[88], pnts[84]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[84], pnts[145], pnts[149]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[150], pnts[149], pnts[145]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[145], pnts[146], pnts[150]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[151], pnts[150], pnts[146]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[146], pnts[147], pnts[151]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[152], pnts[151], pnts[147]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[147], pnts[148], pnts[152]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[153], pnts[92], pnts[88]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[88], pnts[149], pnts[153]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[154], pnts[153], pnts[149]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[149], pnts[150], pnts[154]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[155], pnts[154], pnts[150]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[150], pnts[151], pnts[155]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[156], pnts[155], pnts[151]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[151], pnts[152], pnts[156]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[157], pnts[96], pnts[92]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[92], pnts[153], pnts[157]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[158], pnts[157], pnts[153]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[153], pnts[154], pnts[158]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[159], pnts[158], pnts[154]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[154], pnts[155], pnts[159]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[160], pnts[159], pnts[155]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[155], pnts[156], pnts[160]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[161], pnts[100], pnts[96]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[96], pnts[157], pnts[161]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[162], pnts[161], pnts[157]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[157], pnts[158], pnts[162]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[163], pnts[162], pnts[158]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[158], pnts[159], pnts[163]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[164], pnts[163], pnts[159]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[159], pnts[160], pnts[164]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[165], pnts[104], pnts[100]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[100], pnts[161], pnts[165]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[166], pnts[165], pnts[161]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[161], pnts[162], pnts[166]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[167], pnts[166], pnts[162]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[162], pnts[163], pnts[167]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[168], pnts[167], pnts[163]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[163], pnts[164], pnts[168]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[169], pnts[108], pnts[104]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[104], pnts[165], pnts[169]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[170], pnts[169], pnts[165]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[165], pnts[166], pnts[170]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[171], pnts[170], pnts[166]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[166], pnts[167], pnts[171]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[172], pnts[171], pnts[167]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[167], pnts[168], pnts[172]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[173], pnts[112], pnts[108]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[108], pnts[169], pnts[173]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[174], pnts[173], pnts[169]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[169], pnts[170], pnts[174]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[175], pnts[174], pnts[170]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[170], pnts[171], pnts[175]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[176], pnts[175], pnts[171]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[171], pnts[172], pnts[176]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[177], pnts[116], pnts[112]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[112], pnts[173], pnts[177]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[178], pnts[177], pnts[173]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[173], pnts[174], pnts[178]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[179], pnts[178], pnts[174]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[174], pnts[175], pnts[179]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[180], pnts[179], pnts[175]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[175], pnts[176], pnts[180]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[181], pnts[120], pnts[116]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[116], pnts[177], pnts[181]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[182], pnts[181], pnts[177]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[177], pnts[178], pnts[182]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[183], pnts[182], pnts[178]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[178], pnts[179], pnts[183]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[184], pnts[183], pnts[179]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[179], pnts[180], pnts[184]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[185], pnts[124], pnts[120]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[120], pnts[181], pnts[185]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[186], pnts[185], pnts[181]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[181], pnts[182], pnts[186]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[187], pnts[186], pnts[182]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[182], pnts[183], pnts[187]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[188], pnts[187], pnts[183]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[183], pnts[184], pnts[188]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[189], pnts[128], pnts[124]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[124], pnts[185], pnts[189]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[190], pnts[189], pnts[185]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[185], pnts[186], pnts[190]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[191], pnts[190], pnts[186]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[186], pnts[187], pnts[191]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[192], pnts[191], pnts[187]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[187], pnts[188], pnts[192]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[193], pnts[132], pnts[128]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[128], pnts[189], pnts[193]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[194], pnts[193], pnts[189]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[189], pnts[190], pnts[194]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[195], pnts[194], pnts[190]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[190], pnts[191], pnts[195]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[196], pnts[195], pnts[191]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[191], pnts[192], pnts[196]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[197], pnts[136], pnts[132]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[132], pnts[193], pnts[197]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[198], pnts[197], pnts[193]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[193], pnts[194], pnts[198]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[199], pnts[198], pnts[194]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[194], pnts[195], pnts[199]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[200], pnts[199], pnts[195]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[195], pnts[196], pnts[200]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[201], pnts[140], pnts[136]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[136], pnts[197], pnts[201]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[202], pnts[201], pnts[197]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[197], pnts[198], pnts[202]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[203], pnts[202], pnts[198]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[198], pnts[199], pnts[203]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[204], pnts[203], pnts[199]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[199], pnts[200], pnts[204]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[205], pnts[144], pnts[140]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[140], pnts[201], pnts[205]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[206], pnts[205], pnts[201]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[201], pnts[202], pnts[206]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[207], pnts[206], pnts[202]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[202], pnts[203], pnts[207]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[208], pnts[207], pnts[203]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[203], pnts[204], pnts[208]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[145], pnts[84], pnts[144]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[144], pnts[205], pnts[145]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[146], pnts[145], pnts[205]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[205], pnts[206], pnts[146]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[147], pnts[146], pnts[206]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[206], pnts[207], pnts[147]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[148], pnts[147], pnts[207]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[207], pnts[208], pnts[148]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[213], pnts[152], pnts[148]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[148], pnts[209], pnts[213]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[214], pnts[213], pnts[209]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[209], pnts[210], pnts[214]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[215], pnts[214], pnts[210]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[210], pnts[211], pnts[215]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[215], pnts[211]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[216], pnts[156], pnts[152]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[152], pnts[213], pnts[216]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[217], pnts[216], pnts[213]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[213], pnts[214], pnts[217]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[218], pnts[217], pnts[214]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[214], pnts[215], pnts[218]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[218], pnts[215]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[219], pnts[160], pnts[156]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[156], pnts[216], pnts[219]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[220], pnts[219], pnts[216]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[216], pnts[217], pnts[220]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[221], pnts[220], pnts[217]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[217], pnts[218], pnts[221]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[221], pnts[218]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[222], pnts[164], pnts[160]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[160], pnts[219], pnts[222]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[223], pnts[222], pnts[219]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[219], pnts[220], pnts[223]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[224], pnts[223], pnts[220]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[220], pnts[221], pnts[224]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[224], pnts[221]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[225], pnts[168], pnts[164]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[164], pnts[222], pnts[225]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[226], pnts[225], pnts[222]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[222], pnts[223], pnts[226]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[227], pnts[226], pnts[223]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[223], pnts[224], pnts[227]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[227], pnts[224]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[228], pnts[172], pnts[168]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[168], pnts[225], pnts[228]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[229], pnts[228], pnts[225]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[225], pnts[226], pnts[229]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[230], pnts[229], pnts[226]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[226], pnts[227], pnts[230]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[230], pnts[227]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[231], pnts[176], pnts[172]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[172], pnts[228], pnts[231]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[232], pnts[231], pnts[228]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[228], pnts[229], pnts[232]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[233], pnts[232], pnts[229]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[229], pnts[230], pnts[233]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[233], pnts[230]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[234], pnts[180], pnts[176]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[176], pnts[231], pnts[234]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[235], pnts[234], pnts[231]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[231], pnts[232], pnts[235]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[236], pnts[235], pnts[232]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[232], pnts[233], pnts[236]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[236], pnts[233]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[237], pnts[184], pnts[180]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[180], pnts[234], pnts[237]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[238], pnts[237], pnts[234]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[234], pnts[235], pnts[238]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[239], pnts[238], pnts[235]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[235], pnts[236], pnts[239]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[239], pnts[236]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[240], pnts[188], pnts[184]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[184], pnts[237], pnts[240]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[241], pnts[240], pnts[237]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[237], pnts[238], pnts[241]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[242], pnts[241], pnts[238]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[238], pnts[239], pnts[242]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[242], pnts[239]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[243], pnts[192], pnts[188]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[188], pnts[240], pnts[243]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[244], pnts[243], pnts[240]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[240], pnts[241], pnts[244]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[245], pnts[244], pnts[241]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[241], pnts[242], pnts[245]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[245], pnts[242]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[246], pnts[196], pnts[192]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[192], pnts[243], pnts[246]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[247], pnts[246], pnts[243]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[243], pnts[244], pnts[247]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[248], pnts[247], pnts[244]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[244], pnts[245], pnts[248]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[248], pnts[245]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[249], pnts[200], pnts[196]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[196], pnts[246], pnts[249]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[250], pnts[249], pnts[246]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[246], pnts[247], pnts[250]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[251], pnts[250], pnts[247]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[247], pnts[248], pnts[251]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[251], pnts[248]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[252], pnts[204], pnts[200]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[200], pnts[249], pnts[252]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[253], pnts[252], pnts[249]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[249], pnts[250], pnts[253]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[254], pnts[253], pnts[250]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[250], pnts[251], pnts[254]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[254], pnts[251]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[255], pnts[208], pnts[204]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[204], pnts[252], pnts[255]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[256], pnts[255], pnts[252]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[252], pnts[253], pnts[256]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[257], pnts[256], pnts[253]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[253], pnts[254], pnts[257]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[257], pnts[254]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[209], pnts[148], pnts[208]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[208], pnts[255], pnts[209]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[210], pnts[209], pnts[255]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[255], pnts[256], pnts[210]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[211], pnts[210], pnts[256]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[256], pnts[257], pnts[211]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[212], pnts[211], pnts[257]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[264], pnts[263], pnts[258]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[258], pnts[259], pnts[264]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[265], pnts[264], pnts[259]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[259], pnts[260], pnts[265]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[266], pnts[265], pnts[260]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[260], pnts[261], pnts[266]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[267], pnts[266], pnts[261]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[261], pnts[262], pnts[267]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[269], pnts[268], pnts[263]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[263], pnts[264], pnts[269]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[270], pnts[269], pnts[264]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[264], pnts[265], pnts[270]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[271], pnts[270], pnts[265]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[265], pnts[266], pnts[271]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[272], pnts[271], pnts[266]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[266], pnts[267], pnts[272]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[274], pnts[273], pnts[268]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[268], pnts[269], pnts[274]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[275], pnts[274], pnts[269]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[269], pnts[270], pnts[275]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[276], pnts[275], pnts[270]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[270], pnts[271], pnts[276]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[277], pnts[276], pnts[271]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[271], pnts[272], pnts[277]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[279], pnts[278], pnts[273]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[273], pnts[274], pnts[279]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[280], pnts[279], pnts[274]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[274], pnts[275], pnts[280]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[281], pnts[280], pnts[275]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[275], pnts[276], pnts[281]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[282], pnts[281], pnts[276]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[276], pnts[277], pnts[282]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[284], pnts[283], pnts[278]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[278], pnts[279], pnts[284]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[285], pnts[284], pnts[279]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[279], pnts[280], pnts[285]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[286], pnts[285], pnts[280]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[280], pnts[281], pnts[286]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[287], pnts[286], pnts[281]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[281], pnts[282], pnts[287]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[289], pnts[288], pnts[283]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[283], pnts[284], pnts[289]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[290], pnts[289], pnts[284]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[284], pnts[285], pnts[290]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[291], pnts[290], pnts[285]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[285], pnts[286], pnts[291]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[292], pnts[291], pnts[286]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[286], pnts[287], pnts[292]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[294], pnts[293], pnts[288]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[288], pnts[289], pnts[294]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[295], pnts[294], pnts[289]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[289], pnts[290], pnts[295]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[296], pnts[295], pnts[290]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[290], pnts[291], pnts[296]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[297], pnts[296], pnts[291]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[291], pnts[292], pnts[297]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[259], pnts[258], pnts[293]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[293], pnts[294], pnts[259]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[260], pnts[259], pnts[294]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[294], pnts[295], pnts[260]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[261], pnts[260], pnts[295]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[295], pnts[296], pnts[261]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[262], pnts[261], pnts[296]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[296], pnts[297], pnts[262]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[302], pnts[267], pnts[262]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[262], pnts[298], pnts[302]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[303], pnts[302], pnts[298]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[298], pnts[299], pnts[303]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[304], pnts[303], pnts[299]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[299], pnts[300], pnts[304]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[305], pnts[304], pnts[300]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[300], pnts[301], pnts[305]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[306], pnts[272], pnts[267]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[267], pnts[302], pnts[306]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[307], pnts[306], pnts[302]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[302], pnts[303], pnts[307]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[308], pnts[307], pnts[303]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[303], pnts[304], pnts[308]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[309], pnts[308], pnts[304]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[304], pnts[305], pnts[309]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[310], pnts[277], pnts[272]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[272], pnts[306], pnts[310]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[311], pnts[310], pnts[306]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[306], pnts[307], pnts[311]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[312], pnts[311], pnts[307]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[307], pnts[308], pnts[312]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[313], pnts[312], pnts[308]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[308], pnts[309], pnts[313]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[314], pnts[282], pnts[277]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[277], pnts[310], pnts[314]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[315], pnts[314], pnts[310]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[310], pnts[311], pnts[315]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[316], pnts[315], pnts[311]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[311], pnts[312], pnts[316]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[317], pnts[316], pnts[312]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[312], pnts[313], pnts[317]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[318], pnts[287], pnts[282]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[282], pnts[314], pnts[318]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[319], pnts[318], pnts[314]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[314], pnts[315], pnts[319]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[320], pnts[319], pnts[315]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[315], pnts[316], pnts[320]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[321], pnts[320], pnts[316]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[316], pnts[317], pnts[321]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[322], pnts[292], pnts[287]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[287], pnts[318], pnts[322]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[323], pnts[322], pnts[318]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[318], pnts[319], pnts[323]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[324], pnts[323], pnts[319]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[319], pnts[320], pnts[324]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[325], pnts[324], pnts[320]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[320], pnts[321], pnts[325]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[326], pnts[297], pnts[292]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[292], pnts[322], pnts[326]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[327], pnts[326], pnts[322]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[322], pnts[323], pnts[327]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[328], pnts[327], pnts[323]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[323], pnts[324], pnts[328]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[329], pnts[328], pnts[324]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[324], pnts[325], pnts[329]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[298], pnts[262], pnts[297]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[297], pnts[326], pnts[298]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[299], pnts[298], pnts[326]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[326], pnts[327], pnts[299]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[300], pnts[299], pnts[327]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[327], pnts[328], pnts[300]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[301], pnts[300], pnts[328]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[328], pnts[329], pnts[301]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[336], pnts[335], pnts[330]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[330], pnts[331], pnts[336]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[337], pnts[336], pnts[331]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[331], pnts[332], pnts[337]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[338], pnts[337], pnts[332]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[332], pnts[333], pnts[338]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[339], pnts[338], pnts[333]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[333], pnts[334], pnts[339]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[341], pnts[340], pnts[335]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[335], pnts[336], pnts[341]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[342], pnts[341], pnts[336]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[336], pnts[337], pnts[342]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[343], pnts[342], pnts[337]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[337], pnts[338], pnts[343]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[344], pnts[343], pnts[338]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[338], pnts[339], pnts[344]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[346], pnts[345], pnts[340]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[340], pnts[341], pnts[346]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[347], pnts[346], pnts[341]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[341], pnts[342], pnts[347]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[348], pnts[347], pnts[342]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[342], pnts[343], pnts[348]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[349], pnts[348], pnts[343]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[343], pnts[344], pnts[349]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[351], pnts[350], pnts[345]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[345], pnts[346], pnts[351]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[352], pnts[351], pnts[346]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[346], pnts[347], pnts[352]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[353], pnts[352], pnts[347]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[347], pnts[348], pnts[353]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[354], pnts[353], pnts[348]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[348], pnts[349], pnts[354]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[356], pnts[355], pnts[350]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[350], pnts[351], pnts[356]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[357], pnts[356], pnts[351]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[351], pnts[352], pnts[357]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[358], pnts[357], pnts[352]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[352], pnts[353], pnts[358]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[359], pnts[358], pnts[353]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[353], pnts[354], pnts[359]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[361], pnts[360], pnts[355]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[355], pnts[356], pnts[361]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[362], pnts[361], pnts[356]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[356], pnts[357], pnts[362]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[363], pnts[362], pnts[357]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[357], pnts[358], pnts[363]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[364], pnts[363], pnts[358]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[358], pnts[359], pnts[364]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[366], pnts[365], pnts[360]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[360], pnts[361], pnts[366]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[367], pnts[366], pnts[361]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[361], pnts[362], pnts[367]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[368], pnts[367], pnts[362]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[362], pnts[363], pnts[368]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[369], pnts[368], pnts[363]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[363], pnts[364], pnts[369]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[331], pnts[330], pnts[365]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[365], pnts[366], pnts[331]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[332], pnts[331], pnts[366]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[366], pnts[367], pnts[332]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[333], pnts[332], pnts[367]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[367], pnts[368], pnts[333]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[334], pnts[333], pnts[368]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[368], pnts[369], pnts[334]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[374], pnts[339], pnts[334]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[334], pnts[370], pnts[374]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[375], pnts[374], pnts[370]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[370], pnts[371], pnts[375]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[376], pnts[375], pnts[371]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[371], pnts[372], pnts[376]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[377], pnts[376], pnts[372]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[372], pnts[373], pnts[377]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[378], pnts[344], pnts[339]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[339], pnts[374], pnts[378]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[379], pnts[378], pnts[374]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[374], pnts[375], pnts[379]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[380], pnts[379], pnts[375]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[375], pnts[376], pnts[380]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[381], pnts[380], pnts[376]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[376], pnts[377], pnts[381]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[382], pnts[349], pnts[344]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[344], pnts[378], pnts[382]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[383], pnts[382], pnts[378]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[378], pnts[379], pnts[383]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[384], pnts[383], pnts[379]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[379], pnts[380], pnts[384]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[385], pnts[384], pnts[380]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[380], pnts[381], pnts[385]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[386], pnts[354], pnts[349]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[349], pnts[382], pnts[386]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[387], pnts[386], pnts[382]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[382], pnts[383], pnts[387]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[388], pnts[387], pnts[383]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[383], pnts[384], pnts[388]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[389], pnts[388], pnts[384]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[384], pnts[385], pnts[389]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[390], pnts[359], pnts[354]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[354], pnts[386], pnts[390]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[391], pnts[390], pnts[386]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[386], pnts[387], pnts[391]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[392], pnts[391], pnts[387]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[387], pnts[388], pnts[392]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[393], pnts[392], pnts[388]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[388], pnts[389], pnts[393]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[394], pnts[364], pnts[359]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[359], pnts[390], pnts[394]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[395], pnts[394], pnts[390]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[390], pnts[391], pnts[395]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[396], pnts[395], pnts[391]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[391], pnts[392], pnts[396]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[397], pnts[396], pnts[392]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[392], pnts[393], pnts[397]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[398], pnts[369], pnts[364]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[364], pnts[394], pnts[398]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[399], pnts[398], pnts[394]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[394], pnts[395], pnts[399]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[400], pnts[399], pnts[395]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[395], pnts[396], pnts[400]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[401], pnts[400], pnts[396]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[396], pnts[397], pnts[401]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[370], pnts[334], pnts[369]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[369], pnts[398], pnts[370]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[371], pnts[370], pnts[398]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[398], pnts[399], pnts[371]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[372], pnts[371], pnts[399]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[399], pnts[400], pnts[372]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[373], pnts[372], pnts[400]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[400], pnts[401], pnts[373]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[403], pnts[407]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[408], pnts[407], pnts[403]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[403], pnts[404], pnts[408]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[409], pnts[408], pnts[404]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[404], pnts[405], pnts[409]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[410], pnts[409], pnts[405]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[405], pnts[406], pnts[410]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[407], pnts[411]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[412], pnts[411], pnts[407]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[407], pnts[408], pnts[412]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[413], pnts[412], pnts[408]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[408], pnts[409], pnts[413]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[414], pnts[413], pnts[409]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[409], pnts[410], pnts[414]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[411], pnts[415]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[416], pnts[415], pnts[411]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[411], pnts[412], pnts[416]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[417], pnts[416], pnts[412]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[412], pnts[413], pnts[417]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[418], pnts[417], pnts[413]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[413], pnts[414], pnts[418]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[415], pnts[419]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[420], pnts[419], pnts[415]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[415], pnts[416], pnts[420]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[421], pnts[420], pnts[416]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[416], pnts[417], pnts[421]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[422], pnts[421], pnts[417]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[417], pnts[418], pnts[422]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[419], pnts[423]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[424], pnts[423], pnts[419]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[419], pnts[420], pnts[424]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[425], pnts[424], pnts[420]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[420], pnts[421], pnts[425]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[426], pnts[425], pnts[421]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[421], pnts[422], pnts[426]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[423], pnts[427]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[428], pnts[427], pnts[423]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[423], pnts[424], pnts[428]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[429], pnts[428], pnts[424]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[424], pnts[425], pnts[429]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[430], pnts[429], pnts[425]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[425], pnts[426], pnts[430]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[427], pnts[431]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[432], pnts[431], pnts[427]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[427], pnts[428], pnts[432]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[433], pnts[432], pnts[428]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[428], pnts[429], pnts[433]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[434], pnts[433], pnts[429]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[429], pnts[430], pnts[434]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[431], pnts[435]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[436], pnts[435], pnts[431]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[431], pnts[432], pnts[436]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[437], pnts[436], pnts[432]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[432], pnts[433], pnts[437]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[438], pnts[437], pnts[433]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[433], pnts[434], pnts[438]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[435], pnts[439]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[440], pnts[439], pnts[435]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[435], pnts[436], pnts[440]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[441], pnts[440], pnts[436]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[436], pnts[437], pnts[441]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[442], pnts[441], pnts[437]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[437], pnts[438], pnts[442]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[439], pnts[443]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[444], pnts[443], pnts[439]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[439], pnts[440], pnts[444]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[445], pnts[444], pnts[440]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[440], pnts[441], pnts[445]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[446], pnts[445], pnts[441]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[441], pnts[442], pnts[446]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[443], pnts[447]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[448], pnts[447], pnts[443]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[443], pnts[444], pnts[448]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[449], pnts[448], pnts[444]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[444], pnts[445], pnts[449]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[450], pnts[449], pnts[445]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[445], pnts[446], pnts[450]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[447], pnts[451]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[452], pnts[451], pnts[447]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[447], pnts[448], pnts[452]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[453], pnts[452], pnts[448]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[448], pnts[449], pnts[453]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[454], pnts[453], pnts[449]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[449], pnts[450], pnts[454]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[451], pnts[455]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[456], pnts[455], pnts[451]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[451], pnts[452], pnts[456]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[457], pnts[456], pnts[452]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[452], pnts[453], pnts[457]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[458], pnts[457], pnts[453]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[453], pnts[454], pnts[458]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[455], pnts[459]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[460], pnts[459], pnts[455]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[455], pnts[456], pnts[460]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[461], pnts[460], pnts[456]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[456], pnts[457], pnts[461]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[462], pnts[461], pnts[457]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[457], pnts[458], pnts[462]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[459], pnts[463]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[464], pnts[463], pnts[459]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[459], pnts[460], pnts[464]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[465], pnts[464], pnts[460]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[460], pnts[461], pnts[465]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[466], pnts[465], pnts[461]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[461], pnts[462], pnts[466]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[402], pnts[463], pnts[403]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[404], pnts[403], pnts[463]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[463], pnts[464], pnts[404]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[405], pnts[404], pnts[464]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[464], pnts[465], pnts[405]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[406], pnts[405], pnts[465]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[465], pnts[466], pnts[406]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[471], pnts[410], pnts[406]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[406], pnts[467], pnts[471]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[472], pnts[471], pnts[467]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[467], pnts[468], pnts[472]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[473], pnts[472], pnts[468]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[468], pnts[469], pnts[473]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[474], pnts[473], pnts[469]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[469], pnts[470], pnts[474]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[475], pnts[414], pnts[410]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[410], pnts[471], pnts[475]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[476], pnts[475], pnts[471]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[471], pnts[472], pnts[476]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[477], pnts[476], pnts[472]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[472], pnts[473], pnts[477]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[478], pnts[477], pnts[473]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[473], pnts[474], pnts[478]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[479], pnts[418], pnts[414]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[414], pnts[475], pnts[479]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[480], pnts[479], pnts[475]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[475], pnts[476], pnts[480]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[481], pnts[480], pnts[476]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[476], pnts[477], pnts[481]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[482], pnts[481], pnts[477]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[477], pnts[478], pnts[482]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[483], pnts[422], pnts[418]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[418], pnts[479], pnts[483]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[484], pnts[483], pnts[479]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[479], pnts[480], pnts[484]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[485], pnts[484], pnts[480]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[480], pnts[481], pnts[485]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[486], pnts[485], pnts[481]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[481], pnts[482], pnts[486]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[487], pnts[426], pnts[422]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[422], pnts[483], pnts[487]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[488], pnts[487], pnts[483]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[483], pnts[484], pnts[488]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[489], pnts[488], pnts[484]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[484], pnts[485], pnts[489]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[490], pnts[489], pnts[485]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[485], pnts[486], pnts[490]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[491], pnts[430], pnts[426]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[426], pnts[487], pnts[491]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[492], pnts[491], pnts[487]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[487], pnts[488], pnts[492]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[493], pnts[492], pnts[488]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[488], pnts[489], pnts[493]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[494], pnts[493], pnts[489]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[489], pnts[490], pnts[494]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[495], pnts[434], pnts[430]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[430], pnts[491], pnts[495]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[496], pnts[495], pnts[491]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[491], pnts[492], pnts[496]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[497], pnts[496], pnts[492]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[492], pnts[493], pnts[497]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[498], pnts[497], pnts[493]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[493], pnts[494], pnts[498]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[499], pnts[438], pnts[434]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[434], pnts[495], pnts[499]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[500], pnts[499], pnts[495]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[495], pnts[496], pnts[500]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[501], pnts[500], pnts[496]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[496], pnts[497], pnts[501]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[502], pnts[501], pnts[497]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[497], pnts[498], pnts[502]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[503], pnts[442], pnts[438]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[438], pnts[499], pnts[503]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[504], pnts[503], pnts[499]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[499], pnts[500], pnts[504]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[505], pnts[504], pnts[500]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[500], pnts[501], pnts[505]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[506], pnts[505], pnts[501]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[501], pnts[502], pnts[506]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[507], pnts[446], pnts[442]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[442], pnts[503], pnts[507]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[508], pnts[507], pnts[503]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[503], pnts[504], pnts[508]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[509], pnts[508], pnts[504]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[504], pnts[505], pnts[509]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[510], pnts[509], pnts[505]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[505], pnts[506], pnts[510]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[511], pnts[450], pnts[446]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[446], pnts[507], pnts[511]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[512], pnts[511], pnts[507]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[507], pnts[508], pnts[512]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[513], pnts[512], pnts[508]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[508], pnts[509], pnts[513]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[514], pnts[513], pnts[509]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[509], pnts[510], pnts[514]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[515], pnts[454], pnts[450]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[450], pnts[511], pnts[515]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[516], pnts[515], pnts[511]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[511], pnts[512], pnts[516]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[517], pnts[516], pnts[512]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[512], pnts[513], pnts[517]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[518], pnts[517], pnts[513]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[513], pnts[514], pnts[518]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[519], pnts[458], pnts[454]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[454], pnts[515], pnts[519]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[520], pnts[519], pnts[515]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[515], pnts[516], pnts[520]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[521], pnts[520], pnts[516]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[516], pnts[517], pnts[521]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[522], pnts[521], pnts[517]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[517], pnts[518], pnts[522]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[523], pnts[462], pnts[458]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[458], pnts[519], pnts[523]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[524], pnts[523], pnts[519]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[519], pnts[520], pnts[524]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[525], pnts[524], pnts[520]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[520], pnts[521], pnts[525]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[526], pnts[525], pnts[521]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[521], pnts[522], pnts[526]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[527], pnts[466], pnts[462]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[462], pnts[523], pnts[527]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[528], pnts[527], pnts[523]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[523], pnts[524], pnts[528]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[529], pnts[528], pnts[524]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[524], pnts[525], pnts[529]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[530], pnts[529], pnts[525]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[525], pnts[526], pnts[530]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[467], pnts[406], pnts[466]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[466], pnts[527], pnts[467]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[468], pnts[467], pnts[527]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[527], pnts[528], pnts[468]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[469], pnts[468], pnts[528]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[528], pnts[529], pnts[469]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[470], pnts[469], pnts[529]).set_emission(color).set_material(mat), //
                    new Triangle(pnts[529], pnts[530], pnts[470]).set_emission(color).set_material(mat) //
            );
            scene._lights.add(new PointLight(new Color(500, 500, 500), new Point3D(100, 0, -100)) //
                    .setKq(0.000001));

            ImageWriter imageWriter = new ImageWriter("teapot", 800, 800);
            Render render = new Render() //
                    .setCamera(camera) //
                    .setImageWriter(imageWriter) //
                    .setRayTracer(new BasicRayTracer(scene)) //
                    .setMultithreading(3).setDebugPrint();
            render.renderImageForSuperSampling();
            render.printGrid(50, new Color(java.awt.Color.YELLOW));
            render.writeToImage();
        }

    }

}
