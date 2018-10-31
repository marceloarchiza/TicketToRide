package com.emmettito.tickettoride.views.GameActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import com.emmettito.models.PlayerColor;
import com.emmettito.models.Route;
import com.emmettito.models.Space;
import com.emmettito.models.Tuple;
import com.emmettito.tickettoride.Client;
import com.emmettito.tickettoride.R;

import java.util.List;

public class MapView extends View {

    private int width;
    private int height;

    private float circle_radius;
    private float rect_width;
    private float rect_height;
    private float rect_width_padding;
    private float rect_height_padding;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, int width, int height) {
        super(context);
        this.height = height;
        this.width = width;

        this.circle_radius = 0.006968641f * width;
        this.rect_width = 0.017770035f * width;
        this.rect_height = 0.009953162f * height;

        this.rect_width_padding = 0.02f;
        this.rect_height_padding = 0.01f;

        setLayoutParams(new FrameLayout.LayoutParams(this.width,this.height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(R.drawable.ticket_to_ride_map_v2);

        Client data = Client.getInstance();

        List<Integer> routes = data.getTakenRoutes();
        List<Route> allRoutes = data.getAllRoutes();

        for (int i = 0; i < routes.size(); i++) {
            Route route = allRoutes.get(routes.get(i));
            List<Space> spaces = route.getSpaces();
            for (int j = 0; j < spaces.size(); j++) {
                Space s = spaces.get(j);
                String color = getColorHex(route.getPlayerColor());
                drawRect(canvas, s.getX() * width, s.getY() * height, s.getAngle(), color);
            }
        }
    }

    private void drawCircle(Canvas canvas, float x, float y, String color) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        canvas.drawCircle(x, y, circle_radius, paint);
    }

    private void drawRect(Canvas canvas, float x, float y, float angle, String color) {
        canvas.save();
        canvas.rotate(angle, x, y);
        Paint paint=new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x - rect_width, y + rect_height, x + rect_width,y - rect_height, paint);
        canvas.restore();
    }

    public int onRoute(float x, float  y) {
        Client data = Client.getInstance();
        List<Route> routes = data.getAllRoutes();

        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            List<Space> spaces = route.getSpaces();
            for (int j = 0; j < spaces.size(); j++) {
                Space s = spaces.get(j);
                Tuple t = calcNewPosition((int)x, (int)y, (int)(s.getX() * width), (int)(s.getY() * height), s.getAngle());

                Rect rect = getRect(s.getX(), s.getY());
                if (rect.contains((int)t.getX(), (int)t.getY())) {
                    return route.getID();
                }
            }
        }

        return -1;
    }

    private Tuple calcNewPosition(int x, int y, int rect_x, int rect_y, float degrees) {
        if (degrees == 0) {
            return new Tuple(x, y);
        }

        Double rad = Math.toRadians(degrees);

        int diff_x = x - rect_x;
        int diff_y = y - rect_y;

        int new_x = (int)(diff_x * Math.cos(rad) - diff_y * Math.sin(rad));
        int new_y = (int)(diff_x * Math.sin(rad) + diff_y * Math.cos(rad));

        int final_x = new_x + rect_x;
        int final_y = new_y + rect_y;

        return new Tuple(final_x, final_y);
    }

    private Rect getRect(float x, float y) {
        int left = (int)((x - rect_width_padding) * width);
        int top = (int)((y - rect_height_padding) * height);
        int right = (int)((x + rect_width_padding) * width);
        int bottom = (int)((y + rect_height_padding) * height);

        return new Rect(left, top, right, bottom);
    }

    private String getColorHex(PlayerColor color) {
        int colorID;

        if (color != null) {
            switch(color) {
                case Red:
                    colorID = R.color.redPlayer;
                    break;
                case Orange:
                    colorID = R.color.orangePlayer;
                    break;
                case Yellow:
                    colorID = R.color.yellowPlayer;
                    break;
                case Green:
                    colorID = R.color.greenPlayer;
                    break;
                case Blue:
                    colorID = R.color.bluePlayer;
                    break;
                default:
                    colorID = R.color.defaultPlayer;
                    break;
            }
        }
        else {
            colorID = R.color.defaultPlayer;
        }

        return getResources().getString(colorID);
    }
}

