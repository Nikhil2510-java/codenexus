package com.bhasaka.codenexus.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductCardModel {

    @ValueMapValue
    private String productTitle;

    @ValueMapValue
    private String productDescription;

    @ValueMapValue
    private String productImage;

    @ValueMapValue
    private String productImageAlt;

    @ValueMapValue
    private String productPrice;

    @ValueMapValue
    private String productCurrency;

    @ValueMapValue
    private String badgeText;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private Boolean openInNewTab;

    @ValueMapValue
    private Boolean outOfStock;

    @ChildResource
    private List<OfferItem> offers;

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductImageAlt() {
        return productImageAlt;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductCurrency() {
        if (productCurrency == null || productCurrency.trim().isEmpty()) {
            return "INR";
        }
        return productCurrency;
    }

    public String getBadgeText() {
        return badgeText;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getCtaLink() {
        return ctaLink;
    }

    public boolean isOpenInNewTab() {
        return Boolean.TRUE.equals(openInNewTab);
    }

    public boolean isOutOfStock() {
        return Boolean.TRUE.equals(outOfStock);
    }

    public List<OfferItem> getOffers() {
        return offers != null ? offers : Collections.emptyList();
    }

    @Model(
            adaptables = Resource.class,
            defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class OfferItem {

        @ValueMapValue
        private String icon;

        @ValueMapValue
        private String text;

        public String getIcon() {
            return icon;
        }

        public String getText() {
            return text;
        }
    }
}
