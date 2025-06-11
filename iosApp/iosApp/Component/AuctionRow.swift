import SwiftUI
import Shared

struct AuctionRow: View {
    let auction: Auction

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Image + Name + Button
            HStack(spacing: 8) {
                ProductThumbnail(url: auction.product.mainImage?.imageUrlThumb)

                VStack(alignment: .leading, spacing: 4) {
                    Text(auction.product.name)
                        .font(.body)
                        .fontWeight(.semibold)
                        .lineLimit(1)

                    Spacer()
                }
                .frame(height: 100)
            }

            // Description
            Text(auction.product.description_ ?? "")
                .font(.footnote)
                .lineLimit(2)

            // Municipality
            Text(auction.product.municipalityName)
                .font(.footnote)

            // Current bid
            Text("Current bid: \(auction.product.currentBid)")
                .font(.body)
                .fontWeight(.semibold)

            // Categories
            VStack(alignment: .leading, spacing: 4) {
                ForEach([auction.category1, auction.category2, auction.category3].compactMap { $0 }, id: \.id) { category in
                    Text(category.headline)
                        .foregroundColor(.blue)
                }
            }

            // End date
            Text(auction.product.endDate)
                .font(.footnote)
                .fontWeight(.semibold)
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color(uiColor: .systemBackground))
        .clipShape(RoundedRectangle(cornerRadius: 8))
        .shadow(radius: 2)
    }
}

struct ProductThumbnail: View {
    var url: String?

    var body: some View {
        ZStack {
            Color.gray.opacity(0.2)
                .frame(width: 100, height: 100)
                .clipShape(RoundedRectangle(cornerRadius: 8))
            
            Group {
                if let urlString = url, let imageURL = URL(string: urlString) {
                    

                    AsyncImage(url: imageURL) { phase in
                        switch phase {
                        case .empty:
                            ProgressView()
                                .frame(width: 100, height: 100)
                        case .success(let image):
                            image
                                .resizable()
                                .scaledToFill()
                                .frame(width: 100, height: 100)
                                .clipped()
                        case .failure:
                            EmptyView()
                        @unknown default:
                            EmptyView()
                        }
                    }
                } else {
                    Image(systemName: "lock.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 100, height: 100)
                        .foregroundColor(.gray)
                }
            }
            .clipShape(RoundedRectangle(cornerRadius: 8))
        }
    }
}

