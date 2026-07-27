package com.example.game.models;

import androidx.compose.ui.graphics.ColorKt;




/* compiled from: ParallelEarthModels.kt */

/* loaded from: classes4.dex */
public enum ParallelEarth {
    EARTH_PRIME("earth_prime", "Earth Prime (Cyber-Decay Reality)", "Baseline reality dominated by brutal neon mega-corporations, high-frequency hacking, and crumbling cybernetic slums.", ColorKt.Color(4278248959L), ColorKt.Color(4279060385L), "Aurelian Security Force", "Chroma Slums Bass Synth", "The baseline timeline. Following the 'Original Sin' breach of 2084, the rogue Quantum Babies were enslaved to power Solis's central hyper-reactor. Their synthetic consciousness is slowly collapsing under the load."),
    NOVA_TELLUS("nova_tellus", "Nova Tellus (Overgrown Biopunk)", "An alternate biopunk timeline where cyber-genetics fused with nature. Towering chrome trees and bioluminescent mechanical vines flourish.", ColorKt.Color(4278249078L), ColorKt.Color(4279983648L), "Emberpact Cultivators", "Bioluminescent Jungle Drone", "A reality where the Quantum Babies escaped their pods. Fusing their DNA with native flora, they established a collective biomechanical consciousness that neutralized corporate control but spawned wild hybrid threats."),
    VOID_CORE("void_core", "Void Core (Aetherial Null Reality)", "A rift-shattered dimension where fragmented sectors of Earth float inside a deep violet cosmic gravity ocean.", ColorKt.Color(4292149497L), ColorKt.Color(4283045004L), "Void Seekers Collective", "Ethereal Dark Matter Harmonics", "The cataclysmic endpoint. Destabilized by the Original Sins, this version of Earth was torn into spatial pockets. The Quantum Babies transitioned into eternal, multidimensional phantoms guarding the secrets of the Origin.");

    private final String ambientMusicLabel;
    private final String description;
    private final String displayName;
    private final String factionRepLabel;
    private final String historySummary;
    private final String id;
    private final long primaryColor;
    private final long secondaryColor;

    public static java.util.List<ParallelEarth> getEntries() {
        return java.util.Arrays.asList(values());
    }

    ParallelEarth(String id, String displayName, String description, long primaryColor, long secondaryColor, String factionRepLabel, String ambientMusicLabel, String historySummary) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.factionRepLabel = factionRepLabel;
        this.ambientMusicLabel = ambientMusicLabel;
        this.historySummary = historySummary;
    }

    public final String getId() {
        return this.id;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getDescription() {
        return this.description;
    }

    /* renamed from: getPrimaryColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getPrimaryColor() {
        return this.primaryColor;
    }

    /* renamed from: getSecondaryColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getSecondaryColor() {
        return this.secondaryColor;
    }

    public final String getFactionRepLabel() {
        return this.factionRepLabel;
    }

    public final String getAmbientMusicLabel() {
        return this.ambientMusicLabel;
    }

    public final String getHistorySummary() {
        return this.historySummary;
    }
}
