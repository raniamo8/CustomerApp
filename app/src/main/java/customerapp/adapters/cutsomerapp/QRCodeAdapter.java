package customerapp.adapters.cutsomerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerapp.R;
import customerapp.fragments.customerapp.QRCodeDisplayFragment;
import customerapp.models.customerapp.Address;
import customerapp.models.customerapp.AddressBook;
import customerapp.models.customerapp.Recipient;

import java.io.File;
import java.util.List;

/**
 * Adapter class for displaying and managing QR codes and associated recipient information in a RecyclerView.
 */
public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {
    private List<String> qrCodeFilePaths;
    private Context context;
    private AddressBook addressBook;

    public QRCodeAdapter(Context context, List<String> qrCodeFilePaths, AddressBook addressBook) {
        this.context = context;
        this.qrCodeFilePaths = qrCodeFilePaths;
        this.addressBook = addressBook;
    }

    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qr_code, parent, false);
        return new QRCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, int position) {
        String filePath = qrCodeFilePaths.get(position);
        Bitmap qrCodeBitmap = BitmapFactory.decodeFile(filePath);
        holder.qrCodeItemImageView.setImageBitmap(qrCodeBitmap);
        setRecipientInfo(holder, position);

        /*
        holder.opnQRCodeButton.setOnClickListener(v -> {
            openQRCodeDisplayFragment(v.getContext(), position);
        });
        */

        holder.openQRCodeLayout.setOnClickListener(v -> {
            openQRCodeDisplayFragment(v.getContext(), position);
        });

         /*
        holder.deleteButton.setOnClickListener(v -> {
            deleteQRCodeAndRecipient(position);
        });
           */
    }

    @Override
    public int getItemCount() {
        return qrCodeFilePaths.size();
    }

    static class QRCodeViewHolder extends RecyclerView.ViewHolder {
        ImageView qrCodeItemImageView;
        //Button deleteButton;
        //Button opnQRCodeButton;
        TextView recipientInfoTextView;
        LinearLayout openQRCodeLayout;

        public QRCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            qrCodeItemImageView = itemView.findViewById(R.id.qrCodeItemImageView);
            //deleteButton = itemView.findViewById(R.id.deleteButton);
            //opnQRCodeButton = itemView.findViewById(R.id.openQRCodeButton);
            recipientInfoTextView = itemView.findViewById(R.id.recipientInfoTextView);
            openQRCodeLayout = itemView.findViewById(R.id.openQRCodeLayout);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setRecipientInfo(@NonNull QRCodeViewHolder holder, int position) {
        if (addressBook != null && position < addressBook.getRecipients().size()) {
            Recipient recipient = addressBook.getRecipients().get(position);
            String fullName = recipient.getFirstName() + " " + recipient.getLastName();
            holder.recipientInfoTextView.setText(fullName);
            Log.d("QRCodeAdapter", "Recipient at position " + position + ": " + fullName);
        } else {
            holder.recipientInfoTextView.setText("Unknown Recipient position problem");
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void deleteQRCodeAndRecipient(int position) {
        if (position >= 0 && position < addressBook.getRecipients().size()) {
            Recipient recipient = addressBook.getRecipients().get(position);
            String filePath = context.getDir("qr_codes", Context.MODE_PRIVATE).getAbsolutePath() + "/" + recipient.getQRCodeFileName();
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    qrCodeFilePaths.remove(position);
                    if (position < addressBook.getRecipients().size()) {
                        Address addressToRemove = recipient.getAddresses().get(0);
                        if (recipient.getAddresses().contains(addressToRemove)) {
                            Log.d("AddressBook", "Adresse vor dem Löschen: " + addressToRemove.toString());
                        } else {
                            Log.d("AddressBook", "Adresse nicht gefunden, bevor sie entfernt wird.");
                        }
                    }
                    addressBook.deleteOneRecipient(recipient, context);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, qrCodeFilePaths.size());
                    Toast.makeText(context, "QR-Code gelöscht.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fehler beim Löschen des QR-Codes.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Log.d("QRCodeAdapter", "Fehler: Ungültige Position.");
        }
        Log.d("QRCodeAdapter", "QR-Code an Position " + position + " wird gelöscht.");
        notifyDataSetChanged();
    }


    private void openQRCodeDisplayFragment(Context context, int position) {
        if (position >= 0 && position < qrCodeFilePaths.size()) {
            String filePath = qrCodeFilePaths.get(position);
            QRCodeDisplayFragment fragment = new QRCodeDisplayFragment();
            Bundle bundle = new Bundle();
            bundle.putString("qrCodeFilePath", filePath);
            bundle.putInt("recipientIndex", position);
            fragment.setArguments(bundle);

            if (this.context instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) this.context;
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out)
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }


}