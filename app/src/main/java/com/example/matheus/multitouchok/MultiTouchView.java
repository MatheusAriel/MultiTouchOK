package com.example.matheus.multitouchok;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;


public class MultiTouchView extends View
{

    float dedo1InicioX = 0f;
    float dedo1InicioY = 0f;
    float dedo1FinalX = 0f;
    float dedo1FinalY = 0f;
    float vD1x = 0f;
    float vD1y = 0f;
    String vetorDedo1;

    float dedo2InicioX = 0f;
    float dedo2InicioY = 0f;
    float dedo2FinalX = 0f;
    float dedo2FinalY = 0f;
    float vD2x = 0f;
    float vD2y = 0f;
    String vetorDedo2;

    float vFx = 0f;
    float vFy = 0f;
    String vetorFinal;
    String modo;
    float distanciaFinal = 0f;
    float distanciaInicial;

    private static final int SIZE = 55;
    private SparseArray<PointF> mActivePointers;
    private Paint mPaint;
    //private int[] colors = {Color.RED, Color.CYAN, Color.MAGENTA, Color.BLACK, Color.BLUE,
    //      Color.GRAY, Color.YELLOW, Color.DKGRAY, Color.LTGRAY, Color.GREEN};
    private int[] colors = {Color.GRAY, Color.CYAN};

    private Paint textPaint, textDedo1, textDedo2, linha;


    public MultiTouchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }


    //METODO QUE DEFINE CORES, TAMANHOS E ESTILO DO CANVAS
    private void initView()
    {
        mActivePointers = new SparseArray<PointF>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // set painter color to a color you like
        mPaint.setColor(Color.BLUE);
        mPaint.setAlpha(60);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textDedo1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        textDedo1.setTextSize(35);
        textDedo1.setColor(Color.BLUE);

        textDedo2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        textDedo2.setTextSize(35);
        textDedo2.setColor(Color.RED);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(35);
        textPaint.setColor(Color.GREEN);
        //textPaint.setTextSize(50);

        linha = new Paint(Paint.ANTI_ALIAS_FLAG);
        linha.setTextSize(35);
        linha.setColor(Color.BLACK);

    }

    public float calculaDistancia(float dX,float d2X, float dY, float d2Y)
    {
        float distancia = (float)Math.sqrt(Math.pow(dX-d2X,2)+ Math.pow(dY - d2Y,2));

        return distancia;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        // get pointer index from the event object
        //CONTADOR DE DEDOS
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        // ID DO DEDO
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        //AÇÃO DO QUE VAI ACONTECER, INDEPENDENTE DA QUANTIDADE DE DEDOS


        //getAction() é para um dedo só, se for mais de um dedo temq  usar o
        //getActionMasked()
        //int acao = event.getAction ();
        int acao = event.getActionMasked();


        switch (acao)
        {

            //CASO UM DEDO TOQUE NA TELA
            case MotionEvent.ACTION_DOWN:
                //CASO MAIS DE UM DEDO TOQUE NA TELA
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                // We have a new pointer. Lets add it to the list of pointers


                //SE FOR DEDO 1 INICIO
                if(pointerIndex==0)
                {
                    PointF f = new PointF();
                    f.x = event.getX(pointerIndex);
                    dedo1InicioX = f.x;
                    f.y = event.getY(pointerIndex);
                    dedo1InicioY = f.y;
                    mActivePointers.put(pointerId, f);
                }


                //SE FO DEDO 2 INICIO
                else if(pointerIndex==1)
                {
                    PointF f = new PointF();
                    f.x = event.getX(pointerIndex);
                    dedo2InicioX = f.x;
                    f.y = event.getY(pointerIndex);
                    dedo2InicioY = f.y;
                    mActivePointers.put(pointerId, f);


                    //distanciaInicial =(float)Math.sqrt(
                    //      ((dedo1InicioX-dedo2InicioX)*(dedo1InicioX-dedo2InicioX))
                    //            +((dedo1InicioY-dedo2InicioY)*(dedo1InicioY-dedo2InicioY))
                    //);


                    distanciaInicial = calculaDistancia(dedo1InicioX, dedo2InicioX, dedo1InicioY, dedo2InicioY);


                }
                else if(pointerIndex > 1)
                {
                    break;
                }

                break;
            }

            //CASO O(S) DEDO(S) SE MOVIMENTE(M)
            case MotionEvent.ACTION_MOVE:
            {
                // a pointer was moved
                int size = event.getPointerCount();
                int i;
                for (i = 0; i < size; i++)
                {

                    PointF point = mActivePointers.get(event.getPointerId(i));
                    if(point!=null)
                    {
                        // X E Y DE QUAL DEDO? O i SERVE PARA DIZER
                        point.x = event.getX(i);
                        point.y = event.getY(i);

                        if(i==0)
                        {
                            //PEGO O X E O Y FINAL E COLOCO NAS VARIAVEIS
                            //dedo1FinalX , dedo1FinalY
                            //dedo1FinalX = (point.x - dedo1InicioX);
                            //dedo1FinalY = (point.y - dedo1InicioY);
                            dedo1FinalX = point.x;
                            dedo1FinalY = point.y;

                            //CALCULA O VETOR DO DEDO 1 (x) (X FINAL - X INICIO)
                            vD1x = (dedo1FinalX - dedo1InicioX);

                            //CALCULA O VETOR DO DEDO 1 (y) (Y FINAL - Y INICIO)
                            vD1y = (dedo1FinalY - dedo1InicioY);

                            vetorDedo1 = ("(" + vD1x + " , " + vD1y + ")");

                            /*
                            dedo2InicioX = 0f;
                            dedo2InicioY = 0f;
                            dedo2FinalX = 0f;
                            dedo2FinalY = 0f;
                            vetorDedo2 = ("");
                            */

                            vFx = 0f;
                            vFy = 0f;
                            vetorFinal = ("");

                            if(size==1)
                            {
                                modo = "DESENHO";
                            }
                        }


                        //SE FOR DOIS DEDOS
                        else if(i==1)
                        {
                            dedo2FinalX = point.x;
                            dedo2FinalY = point.y;

                            //CALCULA O VETOR DO DEDO 2 (x) (X FINAL - X INICIO)
                            vD2x = (dedo2FinalX - dedo2InicioX);

                            //CALCULA O VETOR DO DEDO 2 (y) (Y FINAL - Y INICIO)
                            vD2y = (dedo2FinalY - dedo2InicioY);

                            //JUNTA O RESULTADO NUMA STRING
                            vetorDedo2 = ("(" + vD2x + " , " + vD2y + ")");


                            vFx = vD1x * vD2x;
                            vFy = vD1y * vD2y;

                            // distanciaFinal =(float)Math.sqrt(
                            //       ((dedo1FinalX-dedo2FinalX)*(dedo1FinalX-dedo2FinalX))
                            //             +((dedo1FinalY-dedo2FinalY)*(dedo1FinalY-dedo2FinalY))
                            //);

                            distanciaFinal = calculaDistancia(dedo1FinalX, dedo2FinalX, dedo1FinalY, dedo2FinalY);


                            //distanciaFinal = (float) Math.sqrt(Math.pow(dedo1FinalX - dedo2FinalX, 2)
                            //      + Math.pow(dedo1FinalY - dedo2FinalY, 2));

                            vetorFinal = ("(" + vFx + " , " + vFy + ")");

                            if(vFx >= 0&&vFy >= 0)
                            {
                                modo = "PAN";
                            }
                            else //if(vFx < 0||vFy < 0)
                            {

                                if(distanciaFinal > distanciaInicial)
                                {
                                    modo = "Zoom IN";
                                }
                                else //if(distanciaFinal < distanciaInicial)
                                {
                                    modo = "Zoom Out";
                                }

                            }
                            distanciaInicial = distanciaFinal;

                        }
                        else if(i > 1)
                        {
                            break;
                        }

                    }
                }
                break;
            }

            //CASO UM DEDO SAIA DA TELA
            case MotionEvent.ACTION_UP:
                //CASO UM OU MAIS DEDOS SAIAM DA TELA
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            {
                //SE TIROU O DEDO DA TELA, REMOVE ELE DO CONTADOR (mActivePointers)
                mActivePointers.remove(pointerId);
                break;
            }
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // draw all pointers
        int size = mActivePointers.size();
        int i;
        for (i = 0; i < size; i++)
        {
            PointF point = mActivePointers.valueAt(i);
            if(point!=null)
            {
                mPaint.setColor(colors[i % 2]);
            }


            //canvas.drawRect(point.x, point.y, SIZE, 10 ,mPaint);
            canvas.drawCircle(point.x, point.y, SIZE, mPaint);
        }

        //TOTAL DE DEDOS NA TELA(MAX 2)
        canvas.drawText("Total de dedos na tela: " + size, 10, 40, textPaint);

        //VETOR FINAL
        canvas.drawText("VF: " + vetorFinal, 10, 80, textPaint);

        //MODO QUE SE ENCONTRA
        canvas.drawText("Modo: " + modo, 10, 120, textPaint);

        //DISTANCIA INICIAL
        canvas.drawText("Distancia Inicial: " + distanciaInicial, 10, 160, textPaint);

        //DISTANCIA FINAL
        canvas.drawText("Distancia Final: " + distanciaFinal, 10, 200, textPaint);


        canvas.drawText("-----------------------------------------------------------------------------------------------", 10, 220, linha);


        //DEDO 1 INICIO
        canvas.drawText("D1xI: " + dedo1InicioX + "   D1yI: " + dedo1InicioY, 10, 250, textDedo1);

        //DEDO 1 FINAL
        canvas.drawText("D1xF: " + dedo1FinalX + "   D1yF: " + dedo1FinalY, 10, 290, textDedo1);

        //VETOR DEDO 1
        canvas.drawText("VD1: " + vetorDedo1, 10, 330, textDedo1);


        canvas.drawText("-----------------------------------------------------------------------------------------------", 10, 360, linha);


        //DEDO 2 INICIO
        canvas.drawText("D2xI: " + dedo2InicioX + "   D2yI: " + dedo2InicioY, 10, 390, textDedo2);

        //DEDO 2 FINAL
        canvas.drawText("D2xF: " + dedo2FinalX + "   D2yF: " + dedo2FinalY, 10, 430, textDedo2);

        //VETOR DEDO 2
        canvas.drawText("VD2: " + vetorDedo2, 10, 470, textDedo2);


    }

}