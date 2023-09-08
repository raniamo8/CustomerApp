package customerapp.models.customerapp;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;

/**
 * An input filter that excludes emojis and surrogate characters.
 * Use this filter to prevent users from entering such characters in text fields.
 */
public class EmojiExcludeFilter implements InputFilter
{

    /**
     * Filters out emojis and surrogate characters from the input.
     *
     * @param source The original characters being input.
     * @param start  The start of the range of source characters.
     * @param end    The end of the range of source characters.
     * @param dest   The current content.
     * @param dstart The start of the range of destination characters that are being replaced by the new text.
     * @param dend   The end of the range of destination characters that are being replaced by the new text.
     * @return The filtered character sequence without emojis and surrogate characters. 
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
        StringBuilder filtered = new StringBuilder();
        for (int i = start; i < end; i++)
        {
            int type = Character.getType(source.charAt(i));
            if (type != Character.SURROGATE && type != Character.OTHER_SYMBOL)
            {
                filtered.append(source.charAt(i));
            }
        }
        return (source instanceof Spanned) ? new SpannableString(filtered) : filtered.toString();
    }
}
