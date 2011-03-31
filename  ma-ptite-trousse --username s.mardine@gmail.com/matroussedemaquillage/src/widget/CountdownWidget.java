package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import fr.smardine.matroussedemaquillage.R;

public class CountdownWidget extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {

		// called when widgets are deleted

		// see that you get an array of widgetIds which are deleted

		// so handle the delete of multiple widgets in an iteration

		super.onDeleted(context, appWidgetIds);

	}

	@Override
	public void onDisabled(Context context) {

		super.onDisabled(context);

		// runs when all of the instances of the widget are deleted from

		// the home screen

		// here you can do some setup

	}

	@Override
	public void onEnabled(Context context) {

		super.onEnabled(context);

		// runs when all of the first instance of the widget are placed

		// on the home screen

	}

	@Override
	public void onReceive(Context context, Intent intent) {

		// all the intents get handled by this method

		// mainly used to handle self created intents, which are not

		// handled by any other method

		// the super call delegates the action to the other methods

		// for example the APPWIDGET_UPDATE intent arrives here first

		// and the super call executes the onUpdate in this case

		// so it is even possible to handle the functionality of the

		// other methods here

		// or if you don't call super you can overwrite the standard

		// flow of intent handling

		final String action = intent.getAction();

		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			Bundle extras = intent.getExtras();
			final int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {
			super.onReceive(context, intent);
		}

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,

	int[] appWidgetIds) {

		// runs on APPWIDGET_UPDATE

		// here is the widget content set, and updated

		// it is called once when the widget created

		// and periodically as set in the metadata xml

		// the layout modifications can be done using the AppWidgetManager

		// passed in the parameter, we will discuss it later

		// the appWidgetIds contains the Ids of all the widget instances

		// so here you want likely update all of them in an iteration

		// we will use only the first creation run

		for (int appWidgetId : appWidgetIds) {
			RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.countdownwidget);
			appWidgetManager.updateAppWidget(appWidgetId, remoteView);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

}
