import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct AuctionScreen: View {
    let auctionId: Int32

    @StateViewModel
    var viewModel = AuctionViewModel(repository: KoinDependencies().auctionsRepository)

    @State private var bidDialogVisible = false
    @State private var bidInput = ""

    var body: some View {
        VStack(spacing: 16) {
            switch viewModel.state {
            case is AuctionScreenState.Loading:
                Text("Loading data...")

            case let details as AuctionScreenState.Details:
                let auction = details.auction

                TopBar(title: auction.product.name)

                if bidDialogVisible {
                    BidInputDialog(
                        currentBid: auction.product.currentBid,
                        bidInput: $bidInput,
                        onSubmit: {
                            if let bid = Int(bidInput), bid > auction.product.currentBid {
                                viewModel.placeNewBid(bid: Int32(bid))
                                bidDialogVisible = false
                            }
                        },
                        onCancel: {
                            bidDialogVisible = false
                        }
                    )
                }

                ProductLargeImage(url: auction.product.mainImage?.imageUrlLarge)

                Text(auction.product.name)
                    .font(.body)
                    .fontWeight(.semibold)
                    .lineLimit(1)

                Text(auction.product.description_ ?? "")
                    .font(.footnote)
                    .lineLimit(2)

                Text(auction.product.municipalityName)
                    .font(.footnote)

                Text("Current bid: \(auction.product.currentBid)")
                    .font(.body)
                    .fontWeight(.semibold)

                VStack(alignment: .leading, spacing: 4) {
                    ForEach([auction.category1, auction.category2, auction.category3].compactMap { $0 }, id: \.id) { category in
                        Text(category.headline)
                            .foregroundColor(.blue)
                    }
                }

                Text(auction.product.endDate)
                    .font(.footnote)
                    .fontWeight(.semibold)

                Button("Place bid") {
                    bidDialogVisible = true
                }
                .buttonStyle(.borderedProminent)
                .tint(.red)
                .clipShape(RoundedRectangle(cornerRadius: 8))

            case let error as AuctionScreenState.Error:
                Text(error.message)
                    .foregroundColor(.red)

            default:
                EmptyView()
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .onAppear {
            viewModel.initialize(id: auctionId)
        }
    }
}

struct BidInputDialog: View {
    let currentBid: Int32
    @Binding var bidInput: String
    var onSubmit: () -> Void
    var onCancel: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Text("Enter your bid")
                .font(.headline)

            Text("Enter a higher bid than the current:")
                .font(.subheadline)

            TextField("Bid amount", text: $bidInput)
                .keyboardType(.numberPad)
                .textFieldStyle(.roundedBorder)

            HStack {
                Button("Cancel", action: onCancel)
                Spacer()
                Button("Submit", action: onSubmit)
            }
        }
        .padding()
        .background(.ultraThinMaterial)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .shadow(radius: 5)
    }
}


struct ProductLargeImage: View {
    var url: String?

    var body: some View {
        ZStack {
            Color.gray.opacity(0.2)
                .frame(height: 200)
                .clipShape(RoundedRectangle(cornerRadius: 8))
            Group {
                if let url = url, let imageURL = URL(string: url) {
                    AsyncImage(url: imageURL) { image in
                        image.resizable()
                    } placeholder: {
                        ProgressView()
                    }
                } else {
                    EmptyView()
                }
            }
            .frame(height: 200)
            .clipShape(RoundedRectangle(cornerRadius: 8))
        }
    }
}

