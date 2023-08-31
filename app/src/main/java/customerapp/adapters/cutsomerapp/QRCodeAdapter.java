package customerapp.adapters.cutsomerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;

import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.FragmentManagerHelper;
import customerapp.models.customerapp.Recipient;
import customerapp.fragments.customerapp.QRCodeDisplayFragment;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private Context context;

    public QRCodeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qr_code, parent, false);
        return new QRCodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, int position) {
        Recipient recipient = AddressBook.getInstance().getRecipients().get(position);
        holder.recipientInfoTextView.setText(String.format("%s %s", recipient.getFirstName(), recipient.getLastName()));
        //holder.qrCodeItemImageView.setImageBitmap(recipient.generateQRCode());

        holder.openQRCodeLayout.setOnClickListener(v -> {
            openQRCodeDisplayFragment(v.getContext(), position);
        });
    }

    @Override
    public int getItemCount() {
        return AddressBook.getInstance().getRecipients().size();
    }

    static class QRCodeViewHolder extends RecyclerView.ViewHolder {
        ImageView qrCodeItemImageView;
        TextView recipientInfoTextView;
        LinearLayout openQRCodeLayout;

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            qrCodeItemImageView = itemView.findViewById(R.id.qrCodeItemImageView);
            recipientInfoTextView = itemView.findViewById(R.id.recipientInfoTextView);
            openQRCodeLayout = itemView.findViewById(R.id.openQRCodeLayout);
        }
    }

    private void openQRCodeDisplayFragment(Context context, int position) {
        if(position >= 0 && position < AddressBook.getInstance().getRecipients().size()) {
            if (context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) context;
                Recipient selectedRecipient = AddressBook.getInstance().getRecipients().get(position);
                QRCodeDisplayFragment fragment = QRCodeDisplayFragment.newInstance(selectedRecipient);
                FragmentManagerHelper.goToFragment(activity.getSupportFragmentManager(),
                        R.id.frame_layout,
                        fragment,
                        R.anim.slide_in_right,
                        R.anim.slide_out,
                        true);
            }
        } else {
            Log.e("QRCodeAdapter", "Ungültige Position angeklickt: " + position);
        }
    }
}
