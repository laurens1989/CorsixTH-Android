/*
 *   Copyright (C) 2012 Alan Woolley
 *   
 *   See LICENSE.TXT for full license
 */
package uk.co.armedpineapple.corsixth.dialogs;

import java.io.IOException;

import uk.co.armedpineapple.corsixth.Files;
import uk.co.armedpineapple.corsixth.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class DialogFactory {

	public static Dialog createAboutDialog(final Context ctx) {
		final Dialog d = new Dialog(ctx);

		d.setContentView(R.layout.about);

		d.getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		d.setTitle("About");

		Button button = (Button) d.findViewById(R.id.dismissDialogButton);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				d.dismiss();
			}

		});

		d.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				TextView aboutText = (TextView) d
						.findViewById(R.id.aboutTextView);
				String text;
				try {
					text = Files.readTextFromResource(ctx, R.raw.about);
				} catch (IOException e) {
					text = "";
				}
				aboutText.setText(text);
			}

		});
		return d;
	}

	public static Dialog createRecentChangesDialog(final Context ctx) {
		final Dialog d = new Dialog(ctx);
		d.setContentView(R.layout.changes_dialog);
		d.setTitle("Recent Changes");

		Button button = (Button) d.findViewById(R.id.dismissDialogButton);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				d.dismiss();
			}

		});
		d.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				WebView wv = (WebView) d
						.findViewById(R.id.recentChangesWebView);
				wv.loadUrl(ctx.getString(R.string.changes_url));
			}

		});

		return d;

	}

	public static Dialog createNetworkDialog(final Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setMessage(
				ctx.getResources().getString(R.string.no_connection_error))
				.setCancelable(false);

		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}

		});

		return builder.create();
	}

	public static Dialog createExternalStorageDialog(final Context ctx,
			boolean finish) {

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setMessage(
				ctx.getResources().getString(R.string.no_external_storage))
				.setCancelable(false);
		if (finish) {

			builder.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							((Activity) ctx).finish();
						}

					});

		} else {
			builder.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}

					});
		}

		return builder.create();
	}

	public static Dialog createFromException(Exception e, final Context ctx,
			boolean finish) {
		return createFromException(e, null, ctx, finish);
	}

	public static Dialog createFromException(Exception e, String title,
			final Context ctx, boolean finish) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setCancelable(false);
		if (title == null) {
			builder.setTitle("Error!");
		} else {
			builder.setTitle(title);
		}
		builder.setMessage(e.getLocalizedMessage());

		if (finish) {
			builder.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							((Activity) ctx).finish();
						}

					});
		} else {
			builder.setNeutralButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}

					});
		}

		return builder.create();
	}
}
