package lk.ijse.pearlgims.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DialogUtil {

    public enum DialogType {
        INFO, ERROR, CONFIRM, CUSTOM
    }

    /**
     * Shows a customizable dialog
     * @param type The type of dialog
     * @param title Dialog title
     * @param message Dialog message
     * @param confirmText Text for confirm button (null for default)
     * @param cancelText Text for cancel button (null to hide)
     * @param onConfirm Action to run on confirm (null to disable)
     * @param onCancel Action to run on cancel (null for default close)
     * @param graphic Custom graphic node (null for text message)
     */
    public static void showDialog(
            DialogType type,
            String title,
            String message,
            String confirmText,
            String cancelText,
            Runnable onConfirm,
            Runnable onCancel,
            Node graphic
    ) {
        // Validate required parameters
        if (title == null || title.trim().isEmpty()) {
            title = "Dialog";
        }
        if (message == null) {
            message = "";
        }

        // Create dialog layout
        JFXDialogLayout layout = new JFXDialogLayout();

        // Set heading
        Label headingLabel = new Label(title);
        headingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        layout.setHeading(headingLabel);

        // Create body content
        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(20));

        if (graphic != null) {
            body.getChildren().add(graphic);
            // Add message text if both graphic and message are provided
            if (!message.trim().isEmpty()) {
                Text messageText = new Text(message);
                messageText.setTextAlignment(TextAlignment.CENTER);
                messageText.setWrappingWidth(300);
                body.getChildren().add(messageText);
            }
        } else if (!message.trim().isEmpty()) {
            Text messageText = new Text(message);
            messageText.setTextAlignment(TextAlignment.CENTER);
            messageText.setWrappingWidth(300);
            body.getChildren().add(messageText);
        }

        layout.setBody(body);

        // Create dialog - ensure dialogPane is not null
        StackPane dialogPane = ReferenceUtil.dialogPane;
        if (dialogPane == null) {
            throw new IllegalStateException("ReferenceUtils.dialogPane is null. Make sure it's initialized.");
        }

        JFXDialog dialog = new JFXDialog(dialogPane, layout, JFXDialog.DialogTransition.CENTER);
//        dialog.setOverlayClose(false); // Prevent closing by clicking outside

        // Create action buttons
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        actions.setPadding(new Insets(10));

        // Determine button text based on dialog type
        String finalConfirmText = confirmText;
        if (finalConfirmText == null) {
            switch (type) {
                case ERROR:
                    finalConfirmText = "Close";
                    break;
                case CONFIRM:
                    finalConfirmText = "Yes";
                    break;
                default:
                    finalConfirmText = "OK";
                    break;
            }
        }

        // Create confirm button
        JFXButton confirmBtn = new JFXButton(finalConfirmText);
        styleButton(confirmBtn, type, true);
        confirmBtn.setOnAction(e -> {
            try {
                if (onConfirm != null) {
                    onConfirm.run();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                dialog.close();
            }
        });
        if (onConfirm != null) {
            actions.getChildren().add(confirmBtn);
        }

        // Create cancel button for CONFIRM dialogs or when cancelText is provided
        if (type == DialogType.CONFIRM || cancelText != null) {
            String finalCancelText = cancelText != null ? cancelText : "No";
            JFXButton cancelBtn = new JFXButton(finalCancelText);
            styleButton(cancelBtn, type, false);
            cancelBtn.setOnAction(e -> {
                try {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    dialog.close();
                }
            });
            // Add cancel button first for proper order (Cancel, Confirm)
            actions.getChildren().add(0, cancelBtn);
        }

        layout.setActions(actions);

        // Show dialog
        dialog.show();
    }

    /**
     * Styles buttons based on dialog type and button role
     */
    private static void styleButton(JFXButton button, DialogType type, boolean isConfirm) {
        button.setPrefWidth(80);
        button.setPrefHeight(35);

        if (isConfirm) {
            switch (type) {
                case ERROR:
                    button.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 4;");
                    break;
                case CONFIRM:
                    button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 4;");
                    break;
                default:
                    button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 4;");
                    break;
            }
        } else {
            // Cancel button styling
            button.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-background-radius: 4;");
        }

        // Add hover effects
        button.setOnMouseEntered(e -> button.setStyle(button.getStyle() + "-fx-opacity: 0.8;"));
        button.setOnMouseExited(e -> button.setStyle(button.getStyle().replace("-fx-opacity: 0.8;", "")));
    }

    /**
     * Shows a simple information dialog
     */
    public static void showInfo(String title, String message) {
        showDialog(DialogType.INFO, title, message, null, null, null, null, null);
    }

    /**
     * Shows an error dialog
     */
    public static void showError(String title, String message) {
        showDialog(DialogType.ERROR, title, message, null, null, null, null, null);
    }

    /**
     * Shows a confirmation dialog with Yes/No buttons
     */
    public static void showConfirm(String title, String message, Runnable onConfirm) {
        showDialog(DialogType.CONFIRM, title, message, null, null, onConfirm, null, null);
    }

    /**
     * Shows a confirmation dialog with custom confirm action
     */
    public static void showConfirm(String title, String message, Runnable onConfirm, Runnable onCancel) {
        showDialog(DialogType.CONFIRM, title, message, null, null, onConfirm, onCancel, null);
    }

    /**
     * Shows a custom dialog with graphic content
     */
    public static void showCustom(String title, String message, Node graphic, Runnable onConfirm) {
        showDialog(DialogType.CUSTOM, title, message, "Submit", "Cancel", onConfirm, null, graphic);
    }

    /**
     * Shows a custom dialog with full customization
     */
    public static void showCustom(String title, String message, Node graphic,
                                  String confirmText, String cancelText,
                                  Runnable onConfirm, Runnable onCancel) {
        showDialog(DialogType.CUSTOM, title, message, confirmText, cancelText, onConfirm, onCancel, graphic);
    }
}

/*
Sample Usage:

// Simple info dialog
DialogUtil.showInfo("Information", "Operation completed successfully!");

// Error dialog
DialogUtil.showError("Error", "Failed to save the file.");

// Confirmation dialog
DialogUtil.showConfirm("Confirm Delete", "Are you sure you want to delete this item?",
    () -> {
        // Delete action
        System.out.println("Item deleted");
    });

// Confirmation with both actions
DialogUtil.showConfirm("Save Changes", "Do you want to save your changes?",
    () -> System.out.println("Saved"),
    () -> System.out.println("Discarded"));

// Custom dialog with graphic
VBox customContent = new VBox();
customContent.getChildren().addAll(
    new Label("Enter your name:"),
    new TextField()
);
DialogUtil.showCustom("User Input", "", customContent,
    () -> System.out.println("Submitted"));

// Fully customized dialog
DialogUtil.showCustom("Custom Dialog", "Additional message", customContent,
    "Apply", "Reset",
    () -> System.out.println("Applied"),
    () -> System.out.println("Reset"));
*/