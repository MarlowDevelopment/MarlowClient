package net.mommymarlow.marlowclient.mixin;


import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.minecraft.text.OrderedText;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.command.CommandManager;
import net.mommymarlow.marlowclient.gui.clickgui.MarlowClickGui;
import net.mommymarlow.marlowclient.gui.clickgui.components.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {

    @Shadow private ParseResults<CommandSource> parse;
    @Shadow @Final private TextFieldWidget textField;
    @Shadow @Final private MinecraftClient client;
    @Shadow private boolean completingSuggestions;
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow protected abstract void showCommandSuggestions();
    @Shadow private ChatInputSuggestor.SuggestionWindow window;


    @Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onRefresh(CallbackInfo ci, String string, StringReader stringReader) {
        {
            String prefix = MarlowClient.getCommandPrefix();
            int length = prefix.length();
            if (stringReader.canRead(length) && stringReader.getString().startsWith(prefix, stringReader.getCursor())){
                stringReader.setCursor(stringReader.getCursor() + length);
                assert this.client.player != null;
                CommandDispatcher<CommandSource> commandDispatcher = CommandManager.INSTANCE.getDispatcher();
                if (this.parse == null) {
                    this.parse = commandDispatcher.parse(stringReader, CommandManager.INSTANCE.getCommandSource());
                }
                int cursor = textField.getCursor();
                if (cursor >= 1 && (this.window == null || !this.completingSuggestions)) {
                    this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, cursor);
                    this.pendingSuggestions.thenRun(() -> {
                        if (this.pendingSuggestions.isDone()) {
                            this.showCommandSuggestions();
                        }
                    });
            }
                ci.cancel();
            /*

            PASTE FROM REFRESH METHOD


            if (this.parse != null && !this.parse.getReader().getString().equals(string)) {
                this.parse = null;
            }

            if (!this.completingSuggestions) {
                this.textField.setSuggestion((String) null);
                this.window = null;
            }

            this.messages.clear();
            boolean bl = stringReader.canRead() && stringReader.peek() == '#';
            if (bl) {
                stringReader.skip();
            }

            boolean bl2 = this.slashOptional || bl;
            int i = this.textField.getCursor();
            int j;
            if (bl2) {
                CommandDispatcher<CommandSource> commandDispatcher = CommandManager.INSTANCE.getDispatcher();
                if (this.parse == null) {
                    this.parse = commandDispatcher.parse(stringReader, CommandManager.INSTANCE.getCommandSource());
                }

                j = this.suggestingWhenEmpty ? stringReader.getCursor() : 1;
                if (i >= j && (this.window == null || !this.completingSuggestions)) {
                    this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, i);
                    this.pendingSuggestions.thenRun(() -> {
                        if (this.pendingSuggestions.isDone()) {
                            this.showCommandSuggestions();
                        }
                    });
                }
            } else {
                String string2 = string.substring(0, i);
                j = getStartOfCurrentWord(string2);
                Collection<String> collection = this.client.player.networkHandler.getCommandSource().getChatSuggestions();
                this.pendingSuggestions = CommandSource.suggestMatching(collection, new SuggestionsBuilder(string2, j));
             */
            }


        }

    }
}
