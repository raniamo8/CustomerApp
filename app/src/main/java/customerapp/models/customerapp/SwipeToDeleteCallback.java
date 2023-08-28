package customerapp.models.customerapp;

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

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private static final int ICON_SIZE = 100;
    private QRCodeAdapter mAdapter;
    private Drawable deleteIcon;
    private final ColorDrawable background;

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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        new AlertDialog.Builder(viewHolder.itemView.getContext())
                .setTitle("Bestätigen Sie das Löschen")
                .setMessage("Möchten Sie den QR-Code wirklich löschen?")
                .setPositiveButton("Ja", (dialog, which) -> {
                    mAdapter.deleteQRCodeAndRecipient(position);
                })
                .setNegativeButton("Abbrechen", (dialog, which) -> {
                    mAdapter.notifyItemChanged(position);
                })
                .create()
                .show();
    }


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

    private int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}