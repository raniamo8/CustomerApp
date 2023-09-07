
package customerapp.models.customerapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import customerapp.adapters.cutsomerapp.QRCodeAdapter;
import com.example.customerapp.R;

/**
 * Provides swipe to delete functionality for RecyclerView items.
 * The callback listens for left swipe actions and shows an AlertDialog
 * to confirm deletion of the item.
 */
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private static final int ICON_SIZE = 100;
    private QRCodeAdapter mAdapter;
    private Drawable deleteIcon;
    private final ColorDrawable background;


    /**
     * Provides swipe to delete functionality for RecyclerView items.
     * The callback listens for left swipe actions and shows an AlertDialog
     * to confirm deletion of the item.
     */
    public SwipeToDeleteCallback(QRCodeAdapter adapter, Context context) {
        super(0, ItemTouchHelper.LEFT);
        mAdapter = adapter;
        background = new ColorDrawable(Color.RED);
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Executes when a RecyclerView item is swiped.
     * Shows an AlertDialog to confirm the deletion of the item.
     *
     * @param viewHolder The ViewHolder which has been swiped.
     * @param direction  The direction to which the ViewHolder is swiped.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext())
                .setTitle("Bestätigen Sie das Löschen")
                .setMessage("Möchten Sie den QR-Code wirklich löschen?")
                .setPositiveButton("Ja", (dialog, which) -> {
                    AddressBook.getInstance().deleteOneRecipient(position, viewHolder.itemView.getContext());
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Abbrechen", (dialog, which) -> {
                    mAdapter.notifyItemChanged(position);
                    mAdapter.notifyDataSetChanged();
                })
                .setCancelable(true);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.setOnCancelListener(dialog -> {
            mAdapter.notifyItemChanged(position);
            mAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Draws the background and the delete icon when an item is swiped.
     *
     * @param c              The canvas which RecyclerView is drawing its children.
     * @param recyclerView   The RecyclerView to which the ItemTouchHelper is attached.
     * @param viewHolder     The ViewHolder which is being interacted by the user.
     * @param dX             The amount of horizontal displacement caused by user's action.
     * @param dY             The amount of vertical displacement caused by user's action.
     * @param actionState    The type of interaction on the View.
     * @param isCurrentlyActive If the view is currently being controlled by the user.
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int iconSize = dpToPx(ICON_SIZE, recyclerView.getContext());

        ColorDrawable background = new ColorDrawable(Color.RED);
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

        int iconTop = itemView.getTop() + (itemHeight - iconSize) / 2;
        int iconMargin = (itemHeight - iconSize) / 2;
        int iconLeft = itemView.getRight() - iconMargin - iconSize;
        int iconRight = itemView.getRight() - iconMargin;
        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconTop + iconSize);
        deleteIcon.draw(c);
    }

    /**
     * Converts density independent pixels (dp) to pixels (px).
     *
     * @param dp      The value in dp to be converted to px.
     * @param context Current application context.
     * @return The value in pixels (px).
     */
    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}
